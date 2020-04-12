package com.emberestudio.project.ui.components.customexpandablelist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Handler
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import com.emberestudio.project.R
import com.emberestudio.project.ui.planner.adapter.MealPlannerAdapter


class CustomExpandableListView(context: Context, attrs: AttributeSet) :
    ExpandableListView(context, attrs) {

    private var dragMode : Boolean = false
    private val limitHorizontalDrag : Boolean = true
    private val mStartPosition = IntArray(2)
    private val mEndPosition = IntArray(2)
    private var mDragPointOffset = 0
    private var mStartFlatPosition = 0
    private var prevY = -1
    private val backgroundColor = -0x1fefcff0 // different color to identify
    private var defaultBackgroundColor = 0
    private var screenHeight = 0f
    private var dragRatio = 0f
    private var mDragView: ImageView? = null
    private var adapter: MealPlannerAdapter? = null
    private var listeners: DragNDropListeners? = null
    private val dragOffset = 50
    private var dragOnLongPress = false
    private var pressedItem = false
    private val mHandler = Handler()

    init {
        screenHeight = DisplayMetrics().heightPixels.toFloat()
    }

    private fun expandAll(){
        adapter!!.listMeals.forEach{
            expandGroup(it.key)
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return touchHandler(ev)
    }

    override fun setAdapter(adapter: ExpandableListAdapter?) {
        super.setAdapter(adapter)
        this.adapter = adapter as MealPlannerAdapter
//        expandAll()
    }

    fun setDragListener(listener : DragNDropListeners){
        this.listeners = listener
    }


    private fun touchHandler(event: MotionEvent?): Boolean {
        event?.let {
            val action = event.action
            val x = event.x.toInt()
            val y = event.y.toInt()
            if (prevY < 0) {
                prevY = y
            }
            val flatPosition = pointToPosition(x, y)
            dragRatio = height / screenHeight
            val packagedPosition = getExpandableListPosition(flatPosition)
            if (action == MotionEvent.ACTION_DOWN
                && getPackedPositionType(packagedPosition) == 1
            ) {
                if (dragOnLongPress) {
                    if (pressedItem) {
                        dragMode = true
                        pressedItem = false
                    } else {
                        pressedItem = true
                        val r = Runnable { // y coordinate is changing for no reason ??
                            event.setLocation(x.toFloat(), y.toFloat())
                            touchHandler(event)
                        }
                        mHandler.postDelayed(r, 200)
                        return true
                    }
                } else if (x < dragOffset) {
                    dragMode = true
                }
            }
            if (!dragMode) {
                /** when user action on other areas  */
                if (pressedItem && Math.abs(prevY - y) > 30 || event.action != MotionEvent.ACTION_MOVE) {
                    pressedItem = false
                    mHandler.removeCallbacksAndMessages(null)
                }
                return super.onTouchEvent(event)
            }
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    mStartFlatPosition = flatPosition
                    mStartPosition[0] = getPackedPositionGroup(packagedPosition)
                    mStartPosition[1] = getPackedPositionChild(packagedPosition)
                    if (packagedPosition != PACKED_POSITION_VALUE_NULL) {
                        val mItemPosition = flatPosition - firstVisiblePosition
                        mDragPointOffset = y - getChildAt(mItemPosition).top
                        mDragPointOffset -= event.rawY.toInt() - y
                        startDrag(mItemPosition, y)
                        listeners?.onPick(mStartPosition)
                        drag(x, y)
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    val speed = ((y - prevY) * dragRatio).toInt()
                    if (lastVisiblePosition < count && speed > 0) {
                        smoothScrollBy(speed, 1)
                    }
                    if (firstVisiblePosition > 0 && speed < 0) {
                        smoothScrollBy(speed, 1)
                    }
                    drag(x, y) // replace 0 with x if desired
                    listeners?.onDrag(x.toFloat(), y.toFloat())
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    dragMode = false
                    if (getPackedPositionType(packagedPosition) == 0) {
                        mEndPosition[0] = getPackedPositionGroup(packagedPosition)
                        mEndPosition[1] = 0
                    } else {
                        mEndPosition[0] = getPackedPositionGroup(packagedPosition)
                        mEndPosition[1] = getPackedPositionChild(packagedPosition)
                    }
                    stopDrag(mStartFlatPosition)
                    if (packagedPosition != PACKED_POSITION_VALUE_NULL) {
                        adapter?.onDrop(mStartPosition, mEndPosition)
                        listeners?.onDrop(mStartPosition, mEndPosition)
                    }
                }
                else -> {
                    dragMode = false
                    if (getPackedPositionType(packagedPosition) == 0) {
                        mEndPosition[0] = getPackedPositionGroup(packagedPosition)
                        mEndPosition[1] = 0
                    } else {
                        mEndPosition[0] = getPackedPositionGroup(packagedPosition)
                        mEndPosition[1] = getPackedPositionChild(packagedPosition)
                    }
                    stopDrag(mStartFlatPosition)
                    if (packagedPosition != PACKED_POSITION_VALUE_NULL) {
                        adapter?.onDrop(mStartPosition, mEndPosition)
                        listeners?.onDrop(mStartPosition, mEndPosition)
                    }
                }
            }
            prevY = y
        }

        return true
    }

    // enable the drag view for dragging
    private fun startDrag(itemIndex: Int, y: Int) {
        // stopDrag(itemIndex);
        val item = getChildAt(itemIndex) ?: return
        hideItem(item, mStartPosition)

        // Create a copy of the drawing cache so that it does not get recycled
        // by the framework when the list tries to clean up memory
        val bitmap = Bitmap.createBitmap(item.drawingCache)
        item.setBackgroundColor(defaultBackgroundColor)
        val mWindowParams = WindowManager.LayoutParams()
        mWindowParams.gravity = Gravity.TOP
        mWindowParams.x = 0
        mWindowParams.y = y - mDragPointOffset
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams.flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mWindowParams.format = PixelFormat.TRANSLUCENT
        mWindowParams.windowAnimations = 0
        val context = context
        val v = ImageView(context)
        v.setImageBitmap(bitmap)
        val mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(v, mWindowParams)
        mDragView = v
    }

    /**
     * destroy the drag view
     *
     * @param itemIndex
     * Index of the item
     */
    private fun stopDrag(itemIndex: Int) {
        val firstPosition = firstVisiblePosition - headerViewsCount
        val wantedChild = itemIndex - firstPosition
        if (mDragView != null) {
            if (wantedChild < 0 || wantedChild >= childCount) {
                // no need to do anything
            } else {
                showItem(getChildAt(wantedChild))
            }
            mDragView!!.visibility = View.GONE
            val wm = context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager
            wm.removeView(mDragView)
            mDragView!!.setImageDrawable(null)
            mDragView = null
        }
    }

    // move the drag view
    private fun drag(x: Int, y: Int) {
        if (mDragView != null) {
            val layoutParams = mDragView!!.layoutParams as WindowManager.LayoutParams
            if (!limitHorizontalDrag) { // no need to move if horizontal drag is
                // limited
                layoutParams.x = x
            }
            if (dragOnLongPress) {
                // to show that item is detached from the list
                layoutParams.y = y - mDragPointOffset - 20
            } else {
                layoutParams.y = y - mDragPointOffset
            }
            val mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            mWindowManager.updateViewLayout(mDragView, layoutParams)
        }
    }

    fun showItem(itemView: View?) {
        if (itemView != null) {
            itemView.visibility = View.VISIBLE
            itemView.setBackgroundColor(defaultBackgroundColor)
            itemView.isDrawingCacheEnabled = false
            val iv = itemView.findViewById<ImageView>(R.id.move_icon_customizer_item)
            if (iv != null) iv.visibility = View.VISIBLE
        }
    }

    private fun hideItem(itemView: View, position: IntArray) {
        adapter?.onPick(position)
        itemView.visibility = View.INVISIBLE // make the item invisible as we
        // have picked it
        itemView.isDrawingCacheEnabled = true
        defaultBackgroundColor = itemView.drawingCacheBackgroundColor
        itemView.setBackgroundColor(backgroundColor)
        val iv = itemView.findViewById<ImageView>(R.id.move_icon_customizer_item)
        if (iv != null) iv.visibility = View.INVISIBLE
    }

    /**
     * set this to drag an item by long press
     *
     * @param flag
     */
    fun setDragOnLongPress(flag: Boolean) {
        dragOnLongPress = flag
        if (flag) {
        }
    }
}
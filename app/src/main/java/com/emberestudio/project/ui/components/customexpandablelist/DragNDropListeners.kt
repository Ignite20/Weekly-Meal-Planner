/*
 * Copyright (C) 2014 Sreekumar SH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.emberestudio.project.ui.components.customexpandablelist

/**
 * Listeners for the drag and drop listview
 *
 */
interface DragNDropListeners {
    fun onDrag(x: Float, y: Float)

    /**
     * Event fired when an item is picked.
     * position[0] - group position
     * position[1] - child position
     * @param position
     */
    fun onPick(position: IntArray?)

    /**
     * Event fired when an item is dropped.
     * from - position from where item is picked.
     * to - position at which item is dropped.
     * from[0] - group position
     * from[1] - child position
     */
    fun onDrop(from: IntArray?, to: IntArray?)
}
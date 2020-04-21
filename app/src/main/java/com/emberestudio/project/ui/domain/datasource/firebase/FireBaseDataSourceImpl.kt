package com.emberestudio.project.ui.domain.datasource.firebase

import com.emberestudio.project.ui.domain.datasource.*
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import dagger.Module
import javax.inject.Inject

@Module
class FireBaseDataSourceImpl @Inject constructor(val authenticationManager: AuthenticationManager): FireBaseDataSource{

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
    }

    override fun getPlanifications(listener : FireBaseDataSource.OnPlanificationsRetrieved?){
        authenticationManager.getCurrentUser()?.uid?.let { uid ->
            db.collection(PLANS_COLLECTION).whereEqualTo(AUTHOR, uid).get().addOnSuccessListener {
                listener?.onSuccess(it.toObjects(Plan::class.java))
            }
        }
    }

    override fun savePlanification(plan: Plan, listener : FireBaseDataSource.OnPlanificationsRetrieved?) {
        authenticationManager.getCurrentUser()?.uid?.let {
            var mPlan = plan
            mPlan.author = it
            db.collection(PLANS_COLLECTION).add(mPlan).addOnSuccessListener {
                getPlanifications(listener)
            }
        }
    }

    override fun getCurrentUser() : QuerySnapshot? {
        val result = db.collection(USERS_COLLECTION).get()
        return result.result
    }

    override fun saveUser(uuid: String) : Boolean?{

        val result = db.collection(MEALS_COLLECTION).add(uuid)

        return result.isSuccessful
    }

    override fun saveMeal(meal: Meal, listener : FireBaseDataSource.FireBaseListener?){
        db.collection(MEALS_COLLECTION).whereEqualTo(ID, meal.id)
            .get()
            .addOnSuccessListener {
                if(it.documents.size > 0){
                    db.collection(MEALS_COLLECTION).document(it.documents[0].id).set(meal, SetOptions.merge()).addOnSuccessListener {
                        getMeals(listener)
                    }
                }else{
                    db.collection(MEALS_COLLECTION).add(meal).addOnSuccessListener {
                        getMeals(listener)
                    }
                }
            }.addOnFailureListener {

            }
    }

    override fun getMeals(listener : FireBaseDataSource.FireBaseListener?){
        db.collection(MEALS_COLLECTION).get().addOnSuccessListener {
            listener?.onMealsResponse(it.toObjects(Meal::class.java))
        }
    }

    override fun getMeal(id: String, listener : FireBaseDataSource.FireBaseListener?) {
         db.collection(MEALS_COLLECTION).whereEqualTo(ID, id).get().addOnSuccessListener {
             listener?.onItemSaved(it.documents[0].toObject(Meal::class.java))
         }
    }

    override fun removeMeal(id: String, listener: FireBaseDataSource.OnItemRemoved?) {
        db.collection(MEALS_COLLECTION).whereEqualTo(ID, id).get().addOnSuccessListener {
            db.collection(MEALS_COLLECTION).document(it.documents[0].id).delete().addOnSuccessListener {
                listener?.onItemRemoved(id)
            }
        }
    }
}
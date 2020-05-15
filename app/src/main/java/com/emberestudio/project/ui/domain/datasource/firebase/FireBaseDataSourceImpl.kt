package com.emberestudio.project.ui.domain.datasource.firebase

import com.emberestudio.project.ui.domain.datasource.MEALS_COLLECTION
import com.emberestudio.project.ui.domain.datasource.PLANS_COLLECTION
import com.emberestudio.project.ui.domain.datasource.ROLES
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.model.Roles
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import javax.inject.Inject

@Module
class FireBaseDataSourceImpl @Inject constructor(private val authenticationManager: AuthenticationManager): FireBaseDataSource{

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
    }

    override fun getPlan(planId: String, listener: FireBaseDataSource.OnPlanRetrieved?) {
        db.collection(PLANS_COLLECTION).document(planId).get().addOnSuccessListener {
            it.toObject(Plan::class.java)?.let { plan -> listener?.onSuccess(plan) }
        }
    }

    override fun updatePlan(plan: Plan, listener: FireBaseDataSource.OnPlanSaved?){
        db.collection(PLANS_COLLECTION).document(plan.id).set(plan).addOnSuccessListener {
            listener?.onSuccess(plan.id)
        }
    }

    override fun removePlan(planId: String, listener: FireBaseDataSource.OnItemRemoved?) {
        db.collection(PLANS_COLLECTION).document(planId).delete().addOnSuccessListener {
            listener?.onItemRemoved(planId)
        }
    }

    override fun getPlanifications(listener : FireBaseDataSource.OnPlanificationsRetrieved?){
        authenticationManager.getCurrentUser()?.uid?.let { uid ->
            db.collection(PLANS_COLLECTION)
                .whereIn(ROLES.plus(".$uid"), Roles.getNameValues())
                .get()
                .addOnSuccessListener {
                    listener?.onSuccess(it.toObjects(Plan::class.java))
            }
        }
    }

    override fun savePlanification(plan: Plan, listener : FireBaseDataSource.OnPlanSaved?) {
        authenticationManager.getCurrentUser()?.uid?.let {
            plan.roles?.let { roles ->
                roles[it] = Roles.OWNER.nName
            }

            val docRef = db.collection(PLANS_COLLECTION).document()
            plan.id = docRef.id

            db.collection(PLANS_COLLECTION).document(docRef.id).set(plan).addOnSuccessListener {
                listener?.onSuccess(plan.id)
            }
        }
    }

    override fun saveMeal(meal: Meal, listener : FireBaseDataSource.FireBaseListener?){

        if(meal.id.isBlank()) {
            meal.id = db.collection(MEALS_COLLECTION).document().id
        }

        db.collection(MEALS_COLLECTION).document(meal.id).set(meal).addOnSuccessListener {
            getMeals(listener)
        }

    }

    override fun getMeals(listener : FireBaseDataSource.FireBaseListener?){
        db.collection(MEALS_COLLECTION).get().addOnSuccessListener {
            listener?.onMealsResponse(it.toObjects(Meal::class.java))
        }
    }

    override fun getMeal(id: String, listener : FireBaseDataSource.FireBaseListener?) {
         db.collection(MEALS_COLLECTION).document(id).get().addOnSuccessListener {
             listener?.onItemSaved(it.toObject(Meal::class.java))
         }
    }

    override fun removeMeal(id: String, listener: FireBaseDataSource.OnItemRemoved?) {
        db.collection(MEALS_COLLECTION).document(id).delete().addOnSuccessListener {
            listener?.onItemRemoved(id)
        }
    }
}
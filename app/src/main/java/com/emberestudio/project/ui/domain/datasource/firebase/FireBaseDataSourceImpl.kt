package com.emberestudio.project.ui.domain.datasource.firebase

import com.emberestudio.project.ui.domain.datasource.MEALS_COLLECTION
import com.emberestudio.project.ui.domain.datasource.USERS_COLLECTION
import com.emberestudio.project.ui.domain.model.Meal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import dagger.Module
import javax.inject.Inject

@Module
class FireBaseDataSourceImpl @Inject constructor(): FireBaseDataSource{

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
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
        db.collection(MEALS_COLLECTION).add(meal).addOnSuccessListener {
            getMeals(listener)
        }
    }

    override fun getMeals(listener : FireBaseDataSource.FireBaseListener?){
        db.collection(MEALS_COLLECTION).get().addOnSuccessListener {
            listener?.onMealsResponse(it.toObjects(Meal::class.java))
        }
    }

    override fun getMeal(id: String, listener : FireBaseDataSource.FireBaseListener?) {
         db.collection(MEALS_COLLECTION).whereEqualTo("id", id).get().addOnSuccessListener {
             listener?.onItemSaved(it.documents[0].toObject(Meal::class.java))
         }
    }

    override fun removeMeal(id: String, listener: FireBaseDataSource.OnItemRemoved?) {
        db.collection(MEALS_COLLECTION).whereEqualTo("id", id).get().addOnSuccessListener {
            db.collection(MEALS_COLLECTION).document(it.documents[0].id).delete().addOnSuccessListener {
                listener?.onItemRemoved(id)
            }
        }
    }
}
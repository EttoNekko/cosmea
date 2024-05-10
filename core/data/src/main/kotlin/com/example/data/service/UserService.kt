package com.example.data.service

import android.util.Log
import com.example.data.repo.UserRepository
import com.example.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService(private val firestore: FirebaseFirestore): UserRepository {
    override suspend fun addUserData(userData: UserData) {
        val username = userData.username
        val usernamesRef = firestore.collection("users")
            .whereEqualTo("username", username)
        val querySnapshot = usernamesRef.get().await()
        if (!querySnapshot.isEmpty) {
            throw IllegalStateException("Username '$username' already exists. Please choose a different one.")
        }

        firestore.collection("users").
            document(userData.id.toString()).set(userData)
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Created user successfully: $userData")
                }
                .addOnFailureListener {exception ->
                    Log.e("FIRESTORE ERROR", "Error adding user data to Firestore: $exception")
                }.await()
    }

    override suspend fun getUserDataById(userId: String): String? {
        var user: String? = null
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    user = document.data.toString()
                    Log.d("FIRESTORE", "Get user with ID: $userId successfully")
                    Log.d("FIRESTORE", "User: $user")
                } else {
                    Log.d("FIRESTORE ERROR", "User not found with ID: $userId")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error getting user: ", exception)
            }.await()
        return user
    }

    override suspend fun getUserDataByUsername(userName: String): String? {
        var user: String? = null
        firestore.collection("users")
            .whereEqualTo("username", userName).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null) {
                    user = querySnapshot.documents[0].data.toString()
                    Log.d("FIRESTORE", "Get user with username: $userName successfully")
                    Log.d("FIRESTORE", "User: $user")
                } else {
                    Log.d("FIRESTORE ERROR", "User not found with username: $userName")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE ERROR", "Error getting user: ", exception)
            }.await()
        return user
    }

    override suspend fun updateUserData(userId: String, userData: UserData) {
        val user = getUserDataById(userId)
        if (user != null) {
            firestore.collection("users").document(userId).set(userData)
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Updated user successfully: $userData")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error update user data to Firestore: $exception")
                }.await()
        }
        else {
            Log.d("FIRESTORE ERROR", "User not found with ID: $userId")
        }
    }

    override suspend fun deleteUserDataById(userId: String) {
        val user = getUserDataById(userId)
        if (user != null) {
            firestore.collection("users").document(userId).delete()
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Deleted user with ID: $userId successfully")
                }
                .addOnFailureListener { exception ->
                    Log.e("FIRESTORE ERROR", "Error deleting user data: $exception")
                }.await()
        }
        else {
            Log.d("FIRESTORE ERROR", "User not found with ID: $userId")
        }
    }
}
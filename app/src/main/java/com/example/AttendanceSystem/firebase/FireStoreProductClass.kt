/*
package com.example.r.firebase

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.qrscanner.utils.Constants

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class FireStoreProductClass {

    private val mFireStoreClass = FirebaseFirestore.getInstance()

    fun fetchProduct(activity: Activity, productId: String) {
        mFireStoreClass.collection(Constants.PRODUCTS).document(productId).get()
            .addOnSuccessListener { doc ->
                val product = doc.toObject(StylistAd::class.java)
                product?.id = productId
                when (activity) {
                    is StylistAdDetailsActivity -> {
                        activity.fetchProductSuccess(product!!)
                    }
                }
            }.addOnFailureListener { e ->
                when (activity) {
                    is StylistAdDetailsActivity -> {
                        activity.fetchProductFailure(e.message.toString())
                    }
                }
            }
    }

    fun fetchProducts(fragment: Fragment) {
        mFireStoreClass.collection(Constants.PRODUCTS).get()
            .addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                val products = ArrayList<StylistAd>()
                for (doc in docs) {
                    val product = doc.toObject(StylistAd::class.java)
                    product!!.id = doc.id
                    products.add(product)
                }
                when (fragment) {
                    is DashboardFragment -> {
                        fragment.fetchProductsSuccess(products)
                    }
                }
            }.addOnFailureListener { e ->
                when (fragment) {
                    is DashboardFragment -> {
                        fragment.fetchProductsFailure(e.message.toString())
                    }
                }
            }
    }

    fun fetchUserProducts(fragment: Fragment) {
        mFireStoreClass.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, FireStoreAuthClass().getCurrentUserId()).get()
            .addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                val userProducts = ArrayList<StylistAd>()
                for (doc in docs) {
                    val product = doc.toObject(StylistAd::class.java)
                    product!!.id = doc.id
                    userProducts.add(product)
                }
                when (fragment) {
                    is StylistAdFragment -> {
                        fragment.fetchProductsSuccess(userProducts)
                    }
                }
            }.addOnFailureListener { e ->
                when (fragment) {
                    is StylistAdFragment -> {
                        fragment.fetchProductsFailure(e.message.toString())
                    }
                }
            }
    }

    fun addProduct(activity: AddStylistAdActivity, stylistAd: StylistAd) {
        stylistAd.userId = FireStoreAuthClass().getCurrentUserId()!!
        stylistAd.userName = FireStoreAuthClass().getLocalUserName(activity)
        mFireStoreClass.collection(Constants.PRODUCTS).document()
            .set(stylistAd, SetOptions.merge()).addOnSuccessListener {
                activity.addProductSuccess()
            }.addOnFailureListener { e ->
                activity.addProductFailure(e.message.toString())
            }
    }

    fun uploadProductImage(activity: AddStylistAdActivity, imageUri: Uri) {
        val sRef = FirebaseStorage.getInstance().reference.child(
            Constants.PRODUCT_IMAGES_FOLDER +  "/" + FireStoreAuthClass().getCurrentUserId() + "/" +
                    Constants.PRODUCT_IMAGE + System.currentTimeMillis() + Constants.getFileExtension(activity, imageUri))
        sRef.putFile(imageUri).addOnSuccessListener { taskScreenshot ->
            taskScreenshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { imageUri ->
                    activity.imageUploadSuccess(imageUri.toString())
                }
        }.addOnFailureListener { e ->
            activity.addProductFailure(e.message.toString())
        }
    }

    fun deleteProduct(fragment: Fragment, productId: String) {
        mFireStoreClass.collection(Constants.PRODUCTS).document(productId).delete()
            .addOnSuccessListener {
                when (fragment) {
                    is StylistAdFragment -> {
                        fragment.deleteProductSuccess()
                    }
                }
            }.addOnFailureListener {
                when (fragment) {
                    is StylistAdFragment -> {
                        fragment.deleteProductFailure()
                    }
                }
            }
    }

}
*/

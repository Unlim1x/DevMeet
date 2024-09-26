package ru.unlim1x.ui.models

internal class PresenterUi(private val _id :Int,
                           private val _name:String,
                           private val _imageUri:Any,
    val description:String)
    : PersonUi(id = _id, name = _name, imageUri = _imageUri, mainTag = "") {

        fun copy(id :Int = this._id, name:String=this._name, imageUri:Any=this._imageUri, description:String=this.description):PresenterUi{
            return PresenterUi(id,name,imageUri,description)
        }

    override fun equals(other: Any?): Boolean {
        when(other is PresenterUi){
            true -> {
                return this._id == other._id
            }
            false -> return false
        }
    }

}

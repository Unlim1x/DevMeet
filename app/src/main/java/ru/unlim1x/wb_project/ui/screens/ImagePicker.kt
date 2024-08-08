package ru.unlim1x.wb_project.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.viewmodels.image_picker.ImagePickerEvent
import ru.unlim1x.wb_project.ui.viewmodels.image_picker.ImagePickerViewModel
import ru.unlim1x.wb_project.ui.viewmodels.image_picker.ImagePickerViewState



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun ImagePicker(viewModel:ImagePickerViewModel = koinViewModel(),onHideCallback:()->Unit,onDismiss:()->Unit,onImageClick:(uri: Uri)->Unit){
    val sheetState = rememberModalBottomSheetState()
    val viewState by viewModel.viewState().collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.obtain(ImagePickerEvent.PermissionGranted)
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            viewModel.obtain(ImagePickerEvent.PermissionDenied)
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = sheetState,
        containerColor = DevMeetTheme.colorScheme.brandBackground) {
            when (viewState){
                is ImagePickerViewState.Display -> {

                    BottomSheetBody((viewState as ImagePickerViewState.Display).listOfImagesUris){
                        it.path?.let { it1 -> Log.e("BottomSheet", it1) }
                        onImageClick(it)

                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion { onHideCallback() }
                    }
                }
                ImagePickerViewState.DisplayEmpty -> {
                    when (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_IMAGES
                    )) {
                         PackageManager.PERMISSION_GRANTED-> {
                            // Some works that require permission
                            viewModel.obtain(ImagePickerEvent.PermissionGranted)
                        }
                        else -> {
                            // Asking for permission
                            launcher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    }
                }
                ImagePickerViewState.PermissionNotGranted -> {}
                ImagePickerViewState.RequestPermission -> {

                }
            }
    }
    LaunchedEffect(key1 = Unit) {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            launcher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
        else{
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}

@Composable
private fun BottomSheetBody(listUris:List<Uri>,onImageClick:(uri:Uri)->Unit){

    LazyVerticalGrid(columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(20.dp))) {
        itemsIndexed(listUris){index,item->
            ImageInBottomSheetBody(item){
                onImageClick(it)
            }
        }
    }
}

@Composable
private fun ImageInBottomSheetBody(imageUri:Uri, onImageClick:(uri:Uri)->Unit){
    //AsyncImage(model = imageUri, contentDescription = )
    Box(modifier = Modifier
        .clickable { onImageClick(imageUri) }
        .size((LocalConfiguration.current.screenWidthDp / 3).dp)
        .padding(2.dp)) {
        AsyncImage(model = imageUri, contentDescription = "", contentScale = ContentScale.Crop)
    }
}

@Composable
@Preview
private fun ShowImagePicker(){
    ImagePicker(onDismiss = ::doNothing, onHideCallback = ::doNothing){

    }
}

private fun doNothing(){

}
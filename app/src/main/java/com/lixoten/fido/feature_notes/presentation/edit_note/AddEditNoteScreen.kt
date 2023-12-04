package com.lixoten.fido.feature_notes.presentation.edit_note

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.lixoten.fido.R
import com.lixoten.fido.feature_notes.model.Note
import com.lixoten.fido.feature_notes.presentation._components_shared.MyTextField
import com.lixoten.fido.feature_notes.presentation._components_shared.MyTopBar
import com.lixoten.fido.navigation.NavigationDestination
import com.lixoten.fido.ui.theme.Violet
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import com.lixoten.fido.feature_notes.presentation.multimedia.AudioRecorderButton

object EditNoteScreenDestination : NavigationDestination {
    override val route = "edit_note"
    @StringRes
    override val titleRes = R.string.edit_add_note_screen_name
    const val routeArg = "id="
    val routeWithArgs = "$route{$routeArg}"

}

@Composable
fun EditNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddEditNoteViewmodel = viewModel(factory = AddEditNoteViewmodel.Factory),
) {
    // addEditNoteViewModel.setCode(code)
    val uiState by viewModel.uiState.collectAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val noteColor = -1
    val noteBackgroundAnimatable = remember {
        Animatable(
            //Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
            Color(uiState.note.color)
        )
    }
    val scope = rememberCoroutineScope()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewmodel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        topBar = {
            MyTopBar(
                screenTitle = stringResource(id = EditNoteScreenDestination.titleRes),
                canNavigateUp = true,
                navigateUp = { navController.navigateUp() },
                onNavigationIconClick = { }
            )
        },
        floatingActionButton =
        if (uiState.dataHasChanged && !uiState.titleError) {
            {
                FloatingActionButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.onEvent(
                            AddEditNoteEvents.UpdateDbNotes(
                                Note(
                                    0,
                                    "",
                                    "",
                                    0,
                                    false,
                                    false
                                )
                            )
                        )
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(R.string.save_note_img_desc)
                    )
                }
            }
        } else {
            if (!uiState.dataHasChanged && !uiState.titleError) {
                {
                    // Initial Load
                    FloatingActionButton(
                        modifier = Modifier,
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "Add Task")
                    }
                }

            } else {
                {
                }
            }
        }/*,
        bottomBar = {
            MyBottomBar(
                isPinned = uiState.note.isPinned,
                isChecked = uiState.note.isChecked,
                hasDelete = uiState.note.id > 0,
                onDeleteClick = {
                    openDialog = true
                },
                onPinRecord = {
                    viewModel.onEvent(AddEditNoteEvents.UpdateStatePinned)
                },
                onCheckedChange = {
                    viewModel.onEvent(AddEditNoteEvents.UpdateStateCheck)
                }

            )
        }*/,
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }

    ) {
        val focusManager = LocalFocusManager.current
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {

            Row(modifier= modifier
                .height(90.dp)
                .align(Start)
            ) {
                val context = LocalContext.current
                val file = context.createImageFile()
                val uri = FileProvider.getUriForFile(
                    Objects.requireNonNull(context),
                    context.packageName + ".provider", file
                )

                var capturedImageUri by remember {
                    mutableStateOf<Uri>(Uri.EMPTY)
                }

                val cameraLauncher =
                    rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
                        capturedImageUri = uri
                    }

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ){
                    if (it)
                    {
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                        cameraLauncher.launch(uri)
                    }
                    else
                    {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }

                Column{
                    Button(onClick = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED)
                        {
                            cameraLauncher.launch(uri)
                        }
                        else
                        {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Text(text = "Capture Image")
                    }
                }
                Column{
                    if (capturedImageUri.path?.isNotEmpty() == true)
                    {
                        Row{
                            Spacer(Modifier.size(20.dp))
                            Image(
                                painter = rememberImagePainter(capturedImageUri),
                                contentDescription = null,
                                Modifier.height(90.dp)
                            )
                        }
                    }
                    else
                    {
                        Row{
                            Spacer(Modifier.size(80.dp))
                            Image(
                                modifier = Modifier
                                    .padding(16.dp, 8.dp),
                                painter = painterResource(id = R.drawable.ic_image),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Row{
                AudioRecorderButton()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvents.UpdateStatePinned)
                    }
                ) {
                    Icon(
                        painter = if (uiState.note.isPinned) painterResource(id = R.drawable.ic_pin_filled)
                        else painterResource(id = R.drawable.ic_pin),
                        contentDescription = "pin",
                        tint = Violet,
                        modifier = Modifier.size(24.dp),
                    )
                }
                IconButton(
                    onClick = {
                        if (uiState.note.id > 0) {
                            openDialog = true
                        } else {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.LightGray
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Status :")
                    Checkbox(
                        checked = uiState.note.isChecked,
                        onCheckedChange = {
                            viewModel.onEvent(AddEditNoteEvents.UpdateStateCheck)
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

            }
            MyTextField(
                value = uiState.note.title,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvents.UpdateStateTitle(it))
                },
                onDone = { focusManager.moveFocus(FocusDirection.Down) },
                labelResId = if (uiState.titleError) R.string.add_input_label_error else R.string.add_input_label,
                placeHolderResId = R.string.add_input_placeholder,
                error = uiState.titleError,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(uiState.note.color))
            )
            MyTextField(
                value = uiState.note.content,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvents.UpdateStateContent(it))
                },
                labelResId = R.string.add_content_label,
                placeHolderResId = R.string.add_content_placeholder,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color = Color(uiState.note.color))
            )
        }
    }
    if (openDialog) {
        AlertDialog(
            shape = RoundedCornerShape(25.dp),
            onDismissRequest = { openDialog = false },
            title = { Text(stringResource(R.string.remove_note_button)) },
            text = {
                Text(
                    stringResource(
                        R.string.remove_note_confirmation_message,
                        uiState.note.title
                    )
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    shape = RoundedCornerShape(25.dp),
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvents.RemoveDbNote(uiState.note))
                        navController.popBackStack()
                    },
                ) {
                    Text(stringResource(R.string.remove_note_button), color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    shape = RoundedCornerShape(25.dp),
                    onClick = {
                        openDialog = false
                    }) {
                    Text(stringResource(R.string.cancel_button), color = Color.White)
                }
            }
        )
    }
}

@Composable
fun ImageFromCamera(){

}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}


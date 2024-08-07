
package com.example.studysmart.presentation.session

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studysmart.presentation.components.DeleteDialog
import com.example.studysmart.presentation.components.SubjectListButtonShit
import com.example.studysmart.presentation.components.studySessionList
import com.example.studysmart.sessions
import com.example.studysmart.subjects
import com.example.studysmart.util.Constants.ACTION_SERVICE_CANCEL
import com.example.studysmart.util.Constants.ACTION_SERVICE_START
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination(
    deepLinks = [
        DeepLink(
            action = Intent.ACTION_VIEW,
            uriPattern = "study_smart://dashboard/session"
        )
    ]
)
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator
) {

    val viewModel: SessionViewModel = hiltViewModel()
    SessionScreen(
        onBackButtonClick = {navigator.navigateUp()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit

) {
    val context = LocalContext.current
    var sheetState  = rememberModalBottomSheetState()
    var isBottomShitOpen by remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()
    SubjectListButtonShit(
        sheetState = sheetState,
        isOpen = isBottomShitOpen,
        subjects = subjects ,
        onSubjectClicked ={
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible)isBottomShitOpen = false
            }
        },
        onDismissRequest = {isBottomShitOpen = false}
    )
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    DeleteDialog(
        isOpen =isDeleteDialogOpen ,
        title = "Delete Task?",
        bodyText = "Are you sure you want to delete this Task ? All related " +
                "task and study session permanently deleted . This action can not be undone.",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )


    Scaffold(
        topBar = {
            SessionScreenTopBar (onBackButtonClick = onBackButtonClick)
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TimerSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubjectSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedToSubject = "English",
                    selectedSubjectButtonClick = {
                        isBottomShitOpen = true
                    })


            }

            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    startButtonClick = {
                        ServiceHelper.triggerForegroundService(
                            context=context,
                            action = ACTION_SERVICE_START,
                        )
                    },
                    cancelButtonClick = {

                        ServiceHelper.triggerForegroundService(
                            context=context,
                            action = ACTION_SERVICE_CANCEL,
                        )
                    },
                    finishButtonClick = {})


            }

            studySessionList(
                sectionTitle = "STUDY SESSION HISTORY ",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                //sessions = emptyList(),
                sessions = sessions,
                onDeleteIconClick = { isDeleteDialogOpen = true}

            )

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClick: ()->Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack ,
                    contentDescription ="Navigate to Back Screen"
                )

            }
        },
        title = {
            Text(
                text = "Study Sessions",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}


@Composable
private fun TimerSection(modifier: Modifier = Modifier) {
    Box(
        modifier =modifier,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)

        )
        Text(text = "00:05:32",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp))

    }

}

@Composable
private fun RelatedToSubjectSection(
    modifier: Modifier = Modifier,
    relatedToSubject:String,
    selectedSubjectButtonClick:()->Unit
) {
    Column(modifier = modifier) {
        Text(text = "Related to Subject", style = MaterialTheme.typography.bodySmall)
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text = relatedToSubject, style = MaterialTheme.typography.bodyLarge)
            IconButton(
                onClick = selectedSubjectButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription ="Select subject" )

            }
        }

    }


}


@Composable
private fun ButtonSection(
    modifier: Modifier = Modifier,
    startButtonClick:()->Unit,
    cancelButtonClick:()->Unit,
    finishButtonClick:()->Unit,
    ) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick =cancelButtonClick ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Cancel"
            )

        }

        Button(onClick =startButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Start"
            )
        }

        Button(onClick =finishButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Finish"
            )
        }

    }


}
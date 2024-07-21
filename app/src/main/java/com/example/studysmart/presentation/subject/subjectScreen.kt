package com.example.studysmart.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.presentation.components.AddSubjectDialogBox
import com.example.studysmart.presentation.components.CountCard
import com.example.studysmart.presentation.components.DeleteDialog
import com.example.studysmart.presentation.components.studySessionList
import com.example.studysmart.presentation.components.tasksList
import com.example.studysmart.presentation.destinations.TaskScreenRouteDestination
import com.example.studysmart.presentation.task.TaskScreenNavArgs
import com.example.studysmart.sessions
import com.example.studysmart.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SubjectScreenNavArgs(
    var subjectId:Int
)


@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
) {
    SubjectScreen(
        onBackButtonClick = {navigator.navigateUp() },
        onAddTaskButtonClick = {
            val navArg = TaskScreenNavArgs(taskId = null, subjectId = -1)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onTaskCardClick = {
                taskId->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick:()->Unit,
    onTaskCardClick:(Int?)->Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val  isFabExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember{ mutableStateOf("") }
    var goalHours by remember{ mutableStateOf("") }
    var selectedColor by remember {
        mutableStateOf(Subject.subjectCardColors.random())
    }

    AddSubjectDialogBox(
        isOpen =isAddSubjectDialogOpen,
        subjectName =subjectName ,
        goalHours = goalHours,
        onSubjectNameChange ={subjectName = it} ,
        onGoalHoursChange = {goalHours = it},
        selectedColor = selectedColor,
        onColorChange = {selectedColor = it},
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick={
            isAddSubjectDialogOpen = false
        }
    )
    DeleteDialog(
        isOpen =isDeleteSubjectDialogOpen ,
        title = "Delete Subject",
        bodyText = "Are you sure you want to delete this subject ? All related " +
                "task and study session permanently deleted . This action can not be undone.",
        onDismissRequest = { isDeleteSubjectDialogOpen = false },
        onConfirmButtonClick = { isDeleteSubjectDialogOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you want to delete this session ? your study hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopbar(
                title = "English",
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = {isDeleteSubjectDialogOpen = true },
                onEditButtonClick = {isAddSubjectDialogOpen = true },
                scrollBehavior = scrollBehavior,
            )

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskButtonClick,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                text = { Text(text = "Add Task") },
                expanded = isFabExpanded
            )
        }
    ) {
        paddingValue->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)){
            item{
                SubjectOverView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHours = "10",
                    goalHours = "15",
                    progress = 0.75f
                )
            }

            tasksList(
                sectionTitle = "UPCOMING TASK",
                emptyListText = "You don't have any upcoming task. \n" +
                        "Please click on the + icon to add new task.",
                //tasks = emptyList()
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick

            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            tasksList(
                sectionTitle = "Completed TASK",
                emptyListText = "You don't have any completed task. \n" +
                        "Click the check box on completion of task",
                //tasks = emptyList()
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick

            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                //sessions = emptyList(),
                sessions = sessions,
                onDeleteIconClick = {isDeleteDialogOpen=true  }

            )

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopbar(
    title: String,
    onBackButtonClick:()-> Unit,
    onDeleteButtonClick:()->Unit,
    onEditButtonClick:()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
                         IconButton(onClick =  onBackButtonClick ) {
                             Icon(
                                 imageVector = Icons.Default.ArrowBack,
                                 contentDescription ="navigate back" )
                             
                         }
        },
        title = {
            Text(
            text =title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick =  onDeleteButtonClick ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription ="Delete Subject" )

            }
            IconButton(onClick =  onEditButtonClick ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription ="Edit Subject" )

            }
        }
    )
    
}


@Composable
private fun SubjectOverView(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
) {
    val percentageProgress = remember(progress){
        (progress*100).toInt().coerceIn(0,100)      
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier  = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            countWords =studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier  = Modifier.weight(1f),
            headingText = "Study Hours",
            countWords =goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress%")

        }


    }

}
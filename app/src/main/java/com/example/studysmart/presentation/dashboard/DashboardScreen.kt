package com.example.studysmart.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studysmart.R
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.presentation.components.AddSubjectDialogBox
import com.example.studysmart.presentation.components.CountCard
import com.example.studysmart.presentation.components.DeleteDialog
import com.example.studysmart.presentation.components.SubjectCard
import com.example.studysmart.presentation.components.studySessionList
import com.example.studysmart.presentation.components.tasksList
import com.example.studysmart.presentation.destinations.SessionScreenRouteDestination
import com.example.studysmart.presentation.destinations.SubjectScreenRouteDestination
import com.example.studysmart.presentation.destinations.TaskScreenRouteDestination
import com.example.studysmart.presentation.subject.SubjectScreenNavArgs
import com.example.studysmart.presentation.task.TaskScreenNavArgs
import com.example.studysmart.sessions
import com.example.studysmart.subjects
import com.example.studysmart.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start=true)
@Composable
fun DashBoardScreenRoute(
navigator:DestinationsNavigator
) {
    DashboardScreen(
        onSubjectCardClick = {
            subjectId->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId=subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick ={
            taskId->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )


}


@Composable
private fun DashboardScreen(
    onSubjectCardClick:(Int?)->Unit,
    onTaskCardClick:(Int?)->Unit,
    onStartSessionButtonClick:()->Unit,
) {

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
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
        isOpen = isDeleteDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you want to delete this session ? your study hours will be reduced " +
        "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )

    Scaffold(
        topBar = {DashboardScreenTopBar()}
    ) {
        paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp) , subjectCount = 5 , studiedHours ="10" , goalHours = "12" )
            }
            item {
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth() ,
                    //subjectList = emptyList()
                    subjectList = subjects,
                    onAddIconClicked = {
                        isAddSubjectDialogOpen = true
                    },
                    onSubjectCardClick = onSubjectCardClick
                )
            }

            item{
                Button(
                    onClick = onStartSessionButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)

                ) {
                    Text(
                        text ="Start Study Session" )
                }
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

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                "Start a study session to begin recording your progress.",
                //sessions = emptyList(),
                sessions = sessions,
                onDeleteIconClick = { isDeleteDialogOpen = true }

            )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar () {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "StudySmart",
                style = MaterialTheme.typography.headlineMedium

            )
        }
    )
}

@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String,

) {
    Row(modifier =  modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            countWords = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hour",
            countWords = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Studied Hours",
            countWords = goalHours
        )
    }

}


@Composable
private fun SubjectCardSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any Subjects. \n Click the + button to add new Subjects.",
    onAddIconClicked:()->Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall
            )
            IconButton(
                onClick =  onAddIconClicked ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }

        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.image_books),
                contentDescription = emptyListText
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
            ) {
                items(subjectList) { subject ->
                    SubjectCard(
                        subjectName = subject.name,
                        gradientColors = subject.colors,
                        onClick = {onSubjectCardClick(subject.subjectId)}
                    )


                }
            }
        }
    }
}
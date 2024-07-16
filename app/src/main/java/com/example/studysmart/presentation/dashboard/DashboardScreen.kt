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
import com.example.studysmart.presentation.components.SubjectCard
import com.example.studysmart.presentation.components.studySessionList
import com.example.studysmart.presentation.components.tasksList

@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject(name = "English", goalHours = 12f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "Physics", goalHours = 12f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "Math", goalHours = 12f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "Computer", goalHours = 12f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "Geography", goalHours = 12f, colors = Subject.subjectCardColors[0], subjectId = 0),
    )

    val tasks = listOf(
        Task(
            title = "Prepare Notes",
            description = "",
            dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 0,
            taskId = 0
        ),
        Task(
            title = "Do Homework",
            description = "",
            dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = true,
            taskSubjectId = 0,
            taskId = 1
        ),Task(
            title = "Go Coaching",
            description = "",
            dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 0,
            taskId = 1
        ),Task(
            title = "Assignment",
            description = "",
            dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = true,
            taskSubjectId = 0,
            taskId = 1
        ),Task(
            title = "Write Poem",
            description = "",
            dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 0,
            taskId = 1
        ),
    )

    val sessions = listOf(
        Session(
            relatedToSubject = "English",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Physics",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Math",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Computer",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Astrology",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
    )

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
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
                    }
                )
            }

            item{
                Button(
                    onClick = { /*TODO*/ },
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
                onTaskCardClick = {}

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
                onDeleteIconClick = {}

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
    onAddIconClicked:()->Unit
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
                        onClick = {}
                    )


                }
            }
        }
    }
}
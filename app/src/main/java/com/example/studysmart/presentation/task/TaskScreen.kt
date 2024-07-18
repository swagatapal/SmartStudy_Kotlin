package com.example.studysmart.presentation.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studysmart.presentation.components.TaskCheckBox
import com.example.studysmart.presentation.theme.Red


@Composable
fun TaskScreen() {
    Scaffold(
        topBar = {
            TaskScreenTopBar(
                isTaskExits = true,
                isComplete = false,
                checkBoxBorderColor = Red,
                onBackButtonClick = { /*TODO*/ },
                onDeleteButtonClick = { /*TODO*/ },
                onCheckBoxClick = {}
            )

        }
    ) {paddingValue->
        Column(modifier = Modifier.fillMaxSize()
            .padding(paddingValue)
            .padding(horizontal = 12.dp)){

        }
        
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExits: Boolean,
    isComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick:()->Unit,
    onDeleteButtonClick:()->Unit,
    onCheckBoxClick:()->Unit


    ) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick =  onBackButtonClick ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription ="navigate back" )

            }
        },
        title = { Text(text ="Task" ) },
        actions = {
            if(isTaskExits)
            {
                TaskCheckBox(
                    isComplete = isComplete ,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick  ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription ="Delete Task" )

                }

            }
        }
    )
    
}
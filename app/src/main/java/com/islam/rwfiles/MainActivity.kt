package com.islam.rwfiles

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.islam.rwfiles.ui.theme.RWFilesTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RWFilesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var message =""
                    saveDataToExternalStorage(context = LocalContext.current, msg =message)
                }
            }
        }
    }
}

@Composable
fun saveDataToExternalStorage(context: Context, msg: String) {
    // on below line creating a variable for message.
    val message = remember {
        mutableStateOf("")
    }
    val txtMsg = remember {
        mutableStateOf("")
    }
    val activity = context as Activity

    val greenColor = Color.Green

    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // on below line we are adding a text for heading.
        Text(
            // on below line we are specifying text
            text = "External Storage in Android",
            // on below line we are specifying text color,
            // font size and font weight
            color = greenColor,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(10.dp))
        // on below line we are creating a text field for our message number.
        TextField(
            // on below line we are specifying value for our message text field.
            value = message.value,
            // on below line we are adding on value change for text field.
            onValueChange = { message.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your message") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we ar adding single line to it.
            singleLine = true,
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // on below line adding a button.
            Button(
                onClick = {

                    // Requesting Permission to access External Storage
                    // Requesting Permission to access External Storage
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 23
                    )

                    // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
                    // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
                    val folder: File =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                    // Storing the data in file with name as geeksData.txt
                    val file = File(folder, "geeksData.txt")
                    writeTextData(file, message.value, context)
                    message.value = ""
                    // displaying a toast message
                    Toast.makeText(context, "Data saved publicly..", Toast.LENGTH_SHORT).show()

                },
                // on below line adding a modifier for our button.
                modifier = Modifier
                    .padding(5.dp)
                    .width(120.dp)
            ) {
                // on below line adding a text for our button.
                Text(text = "Save Publicly", textAlign = TextAlign.Center)
            }
            // on below line adding a spacer
            Spacer(modifier = Modifier.width(10.dp))
            // on below line creating a button
            Button(
                onClick = {

                    // Creating folder with name GeekForGeeks
                    val folder: File? = context.getExternalFilesDir("GeeksForGeeks")

                    // Creating file with name gfg.txt

                    // Creating file with name gfg.txt
                    val file = File(folder, "gfg.txt")
                    writeTextData(file, message.value, context)

                    message.value = ""
                    // displaying a toast message
                    Toast.makeText(context, "Data saved privately", Toast.LENGTH_SHORT).show()

                },
                // on below line adding a modifier for our button.
                modifier = Modifier
                    .padding(5.dp)
                    .width(120.dp)
            ) {
                // on below line adding a text for our button.
                Text(text = "Save Privately", textAlign = TextAlign.Center)
            }
        }

        // adding spacer on below line.
        Spacer(modifier = Modifier.height(100.dp))

        // on below line adding a text view to display our data.
        Text(
            // on below line we are specifying text
            text = "Data will appear below : \n" + txtMsg.value,
            // on below line we are specifying text color,
            // font size and font weight
            color = greenColor,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // on below line adding a spcaer.
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(all = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // on below line adding a button.
            Button(
                onClick = {
                    // Accessing the saved data from the downloads folder
                    val folder =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                    // geeksData represent the file data that is saved publicly
                    val file = File(folder, "geeksData.txt")
                    val data: String = getdata(file)
                    if (data != null && data != "") {
                        txtMsg.value = data
                    } else {
                        txtMsg.value = "No Data Found"
                    }
                },
                // on below line adding a modifier for our button.
                modifier = Modifier
                    .padding(5.dp)
                    .width(120.dp)
            ) {
                // on below line adding a text for our button.
                Text(text = "View\nPublic", textAlign = TextAlign.Center)
            }
            // on below line adding a spacer
            Spacer(modifier = Modifier.width(10.dp))
            // on below line creating a button
            Button(
                onClick = {

                    // GeeksForGeeks represent the folder name to access privately saved data
                    val folder: File? = context.getExternalFilesDir("GeeksForGeeks")

                    // gft.txt is the file that is saved privately
                    val file = File(folder, "gfg.txt")
                    val data = getdata(file)
                    if (data != null) {
                        txtMsg.value = data
                    } else {
                        txtMsg.value = "No Data Found"
                    }
                },
                // on below line adding a modifier for our button.
                modifier = Modifier
                    .padding(5.dp)
                    .width(120.dp)
            ) {
                // on below line adding a text for our button.
                Text(text = "View\nPrivate", textAlign = TextAlign.Center)
            }
        }
    }
}

// on below line creating get data method to get data from file.
private fun getdata(myfile: File): String {
    // on below line creating a variable for file input stream.
    var fileInputStream: FileInputStream? = null
    // on below line reading data from file and returning it.
    try {
        fileInputStream = FileInputStream(myfile)
        var i = -1
        val buffer = StringBuffer()
        while (fileInputStream.read().also { i = it } != -1) {
            buffer.append(i.toChar())
        }
        return buffer.toString()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        if (fileInputStream != null) {
            try {
                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return ""
}

// on below line creating a method to write data to txt file.
private fun writeTextData(file: File, data: String, context: Context) {
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(data.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
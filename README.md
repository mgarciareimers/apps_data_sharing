# Apps Data Sharing
This project consists of 2 apps that share data.

- App 1: Asks for data to App 2.
- App 2: Sends data back to App 1 (2 strings: a plate number and a Base64 encoded image).

When App 2 gives data to App 1, it shows these data.

RECOMMENDEDATION: I truly recommend to download the apps the test them before getting into the code. To download them you can get the projects and launch them.

I hope it helps. In my case, it took me some time to get this.

# Code snippets

## App 1

When user clicks the button to fetch data (and open App 2), an intent is created. Action and type have to be set. The activity (in App 2) is requested and the current activity stays in the background until it receives the result, which is evaluated in the startActivityForResult method.

IMPORTANT: The action has to be the same as the one specified in App 2 AndroidManifest.xml (shown later). 

```
...

// Open button listener.
openButton.setOnClickListener(view -> {
  Intent intent = new Intent();

  intent.setAction("com.mgarciareimers.app2.getPlate");
  intent.setType("text/plain");

  startActivityForResult.launch(intent);
});

...

// Start activity for result definition.
ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
  if (result.getResultCode() == Activity.RESULT_OK) {
    ...
  } else {
    ...
  }   
});

...
```

## App 2
App 2 has two activities:

- MainActivity: Created as launching activity so user can open it. It shows a small description of the app's usage.
- PlateActivity: Activity that send the data back to the calling app (App 1).

To enable the connection between both apps, it is required to define the intent-filter for the called activity (PlateActivity) in the AndroidManifest.xml (action and mimeType have to be the one defined in App1 - actually it should be the other way around :P, App 1 defines from App 2 and not the other way around):

```
...

<activity
  android:name=".PlateActivity"
  android:exported="true" >
  <intent-filter>
      <action android:name="com.mgarciareimers.app2.getPlate" />
      <category android:name="android.intent.category.DEFAULT" />
      <data android:mimeType="text/plain" />
  </intent-filter>
</activity>
        
...
```

When user clicks the button to send data (to App 1), an intent is created with the data that have to be sent to the other app. Once the result is set, the activity finishes and App 1 activity is moved to the foreground.

```
...

openButton.setOnClickListener(view -> {
  Intent intent = new Intent();

  Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
  ByteArrayOutputStream stream = new ByteArrayOutputStream();
  bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);

  byte[] bytes = stream.toByteArray();


  intent.putExtra("plate", "1234 BCD");
  intent.putExtra("image", Base64.encodeToString(bytes, 0));

  setResult(Activity.RESULT_OK, intent);
  finish();
});

...
```

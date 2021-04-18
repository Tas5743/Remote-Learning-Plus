// URL validation from: https://www.geeksforgeeks.org/check-if-an-url-is-valid-or-not-using-regular-expression/
// URL VALIDATION from https://stackoverflow.com/questions/8660209/how-to-validate-url
// META description extraction from: https://stackoverflow.com/questions/9958425/get-title-meta-description-content-using-url

package com.teamremote.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddResource extends AppCompatActivity {

    // TODO: get course info from previous activity

    Intent intent;
    String course;
    CollectionReference colRef;
    private EditText etURL;
    private EditText etResourceTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        etURL = findViewById(R.id.etURL);
        etResourceTitle = findViewById(R.id.etResourceTitle);
        intent = getIntent();
        course = intent.getStringExtra("course");
        course = "0915U1AvP";
        colRef = FirebaseFirestore.getInstance()
                .collection("/courses/" + course + "/learningResources");

        Button addResource = findViewById(R.id.btnAddResource);
        addResource.setOnClickListener(v -> {

            Map<String, Object> resource = new HashMap<String, Object>();
            resource.put("URL", etURL.getText().toString());
            resource.put("title", etResourceTitle.getText().toString());
            colRef.add(resource);
            openNewResourcePageActivity(course);

        });

    }


    private void openNewResourcePageActivity(String course) {
        Intent intent = new Intent(this, ResourceHome.class);
        intent.putExtra("course", course);
        startActivity(intent);
    }


  /*  String getMetaTag(String document, String attr) throws IOException {

        Document doc = Jsoup.connect(document).get();

        Elements elements = doc.select("meta[name=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        elements = doc.select("meta[property=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        return null;
    }

    public void generatePreview(EditText editTextURL) throws IOException {
        String doc = editTextURL.getText().toString();
        String title = Jsoup.connect(doc).get().title();

        // set preview title
        resourceTitle.setText(title);

        // set preview description
        String description = getMetaTag(doc, "description");
        if (description==null) description = getMetaTag(doc, "og:description");
        description = description.substring(0, Math.min(description.length(), 155));
        resourceText.setText(description);

        //set preview image
        String imgURL = getMetaTag(doc, "og:image");

    }



    public static boolean
    isValidURL(String url)
    {// Regex to check valid URL
        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (url == null) {
            return false;
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(url);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }
*/



}
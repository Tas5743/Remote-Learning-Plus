// URL validation from: https://www.geeksforgeeks.org/check-if-an-url-is-valid-or-not-using-regular-expression/
// URL VALIDATION from https://stackoverflow.com/questions/8660209/how-to-validate-url
// META description extraction from: https://stackoverflow.com/questions/9958425/get-title-meta-description-content-using-url

package com.teamremote.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddResource extends AppCompatActivity {

    // TODO: get course info from previous activity

    private CollectionReference colRef = FirebaseFirestore.getInstance().collection("courses/cmpsc475/learningResources");
    private final FirebaseFirestore db;

    EditText editTextURL= findViewById(R.id.editTextURL);
    ImageView imagePreview = findViewById(R.id.imagePreview);
    TextView resourceTitle = findViewById(R.id.resourceTitle);
    TextView resourceText = findViewById(R.id.resourceText);

    String classTaken = getIntent().getStringExtra().toString();

    public AddResource(FirebaseFirestore db) {
        this.db = db;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);


        editTextURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String inputURL = editTextURL.getText().toString();
                if (!hasFocus) {

                    if (!inputURL.startsWith("http://") && !inputURL.startsWith("https://")) { inputURL = "http://" + inputURL; }

                    if (isValidURL(editTextURL.getText().toString())) {
                        try {
                            generatePreview(findViewById(R.id.editTextURL));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else Toast.makeText(AddResource.this, "Please enter a valid link.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final Button button = findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Map<String, Object> resource = new HashMap<String, Object>();
                resource.put("URL", editTextURL.getText().toString());
                resource.put("title", resourceTitle.getText().toString());
                resource.put("description", resourceTitle.getText().toString());
                resource.put("preview", resourceText.getText().toString());


            }
        });

    }




    String getMetaTag(String document, String attr) throws IOException {

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




   /* void generatePreview(EditText editTextURL) throws IOException {

        String url = editTextURL.getText().toString();
        String title = Jsoup.connect(url).get().title();
        String text = getMetaTag(url, "description");
        if (text == null) text = getMetaTag(url, "og:description");
        String imgURL = getMetaTag(url, "og:image");

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(url))
                // Open links with this app on Android
                .setSocialMetaTagParameters(
                            new DynamicLink.SocialMetaTagParameters().Builder()
                            .setTitle(title)
                            .setDescription(text)
                            .setImageUrl(imgURL)
                            .build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

    }*/

}
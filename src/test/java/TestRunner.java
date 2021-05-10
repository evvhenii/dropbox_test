import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.Test;

public class TestRunner {
    private static final String ACCESS_TOKEN = "irLYJJIM2gIAAAAAAAAAASvCSQ7F2tOp10YiTr0CjapImBsZ9qOPFXlLOULt_l_E";

    @Test
    public void uploadFileTest() throws IOException {
        File file = new File("img.png");
        String jsonString = new JSONObject()
                .put("mode", "add")
                .put("autorename", true)
                .put("path", "/img.png")
                .toString();

        RestAssured.given()
                .headers("Dropbox-API-Arg", jsonString, "Content-Type", "text/plain; charset=dropbox-cors-hack", "Authorization", "Bearer " + ACCESS_TOKEN)
                .body(Files.readAllBytes(file.toPath()))
                .when().post("https://content.dropboxapi.com/2/files/upload")
                .then().statusCode(200);
    }

    @Test
    public void getFileMetadataTest() {
        String jsonString = new JSONObject()
                .put("path", "/img.png")
                .toString();

        RestAssured.given()
                .headers("Authorization", "Bearer " + ACCESS_TOKEN, "Content-Type", "application/json")
                .body(jsonString)
                .when().post("https://api.dropboxapi.com/2/files/get_metadata")
                .then().statusCode(200);

    }

    @Test
    public void deleteFileTest() {
        String jsonString = new JSONObject()
                .put("path", "/img.png")
                .toString();

        RestAssured.given()
                .headers("Authorization", "Bearer " + ACCESS_TOKEN, "Content-Type", "application/json")
                .body(jsonString)
                .when().post("https://api.dropboxapi.com/2/files/delete_v2")
                .then().statusCode(200);
    }
}
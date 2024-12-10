package boilerplate.code.SSF;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class SampleApiCall {

    public static final String SEARCH_URL = "example";
    
    public void search() {

        // 1. Construct the URL (if needed)
        String url = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("apikey", "xyz123") // to add query params to the url (?apikey=xyz123)
                .toUriString();

        // 2A. Construct the Request - for GET (no payload)
        RequestEntity<Void> getReq = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // 2B. Construct the Request - for POST (there is payload --> hence need content-type &
        // RequestEntity<String> instead of RequestEntity<Void>)
        // - json body
        RequestEntity<String> postReq = RequestEntity
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("{}", String.class);

                // or .header("Accept", "application/json")
                // or .header("Content-Type" "application/json")

        // 2C. Construct the Request - for POST, form body
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("name", "apple");

        RequestEntity<MultiValueMap<String, String>> postReqForm = RequestEntity
                .post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(form, MultiValueMap.class);

        // 3. Create Rest Template
        RestTemplate template = new RestTemplate();

        try {
            // 4. Get the reponse using template.exchange(); -> Preferably in try-catch block
            ResponseEntity<String> resp = template.exchange(getReq, String.class);
            String payload = resp.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

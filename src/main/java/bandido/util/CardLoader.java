package bandido.util;

import bandido.model.Card;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardLoader {
    public static List<Card> loadCards(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(path));
        List<Card> cards = new ArrayList<>();
        for (JsonNode node : root) {
            String id = node.get("id").asText();
            List<String> exits = new ArrayList<>();
            for (JsonNode exit : node.get("exits")) {
                exits.add(exit.asText());
            }
            int count = node.get("count").asInt();
            for (int i = 0; i < count; i++) {
                cards.add(new Card(id, exits));
            }
        }
        return cards;
    }
}

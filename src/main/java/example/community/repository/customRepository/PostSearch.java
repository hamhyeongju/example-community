package example.community.repository.customRepository;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSearch {
    private SearchType searchType;
    private String searchWord;

    public boolean isEmpty() {
        return searchType == null || searchWord == null;
    }
}

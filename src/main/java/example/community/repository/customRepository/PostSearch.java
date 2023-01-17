package example.community.repository.customRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * @brief 검색 조건, 검색어 DTO
 */
@Getter @Setter
public class PostSearch {
    private SearchType searchType;
    private String searchWord;

    public boolean isBlank() {
        return searchType == null || searchWord == null;
    }
}

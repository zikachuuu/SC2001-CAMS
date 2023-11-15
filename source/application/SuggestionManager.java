package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.Suggestion;

public class SuggestionManager {
    

    /**
     * Find all suggestions that all students (as camp committee) has submitted
     * @return Arraylist of suggestions.
     */
    protected static ArrayList<Suggestion> findAllSuggestions() {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.isActive()) continue ;

            for (Suggestion suggestion : camp.getSuggestions()) {
                if(suggestion.getActive()) {
                    suggestions.add(suggestion) ;
                }
            }
        }
        return suggestions ;
    }
}

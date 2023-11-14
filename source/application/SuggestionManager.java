package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.Suggestion;

public class SuggestionManager {
    
    protected static ArrayList<Suggestion> findAllSuggestions() {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.getActive()) continue ;

            for (Suggestion suggestion : camp.getSuggestions()) {
                if(suggestion.getActive()) {
                    suggestions.add(suggestion) ;
                }
            }
        }
        return suggestions ;
    }
}

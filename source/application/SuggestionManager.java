package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.Suggestion;
import source.user.Staff;

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
                if(suggestion.isActive()) {
                    suggestions.add(suggestion) ;
                }
            }
        }
        return suggestions ;
    }


    /**
     * Find all suggestions regarding camps under the provided staff.
     * @param staff
     * @param notApproved True for not approved suggestions only, false for all suggestions.
     * @return Arraylist of suggestions
     */
    protected static ArrayList<Suggestion> findAllSuggestions (Staff staff, boolean notApproved) {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>() ;

        for (Camp camp : staff.getCreatedCamps()) {
            if (! camp.isActive()) continue ;

            for (Suggestion suggestion : camp.getSuggestions()) {
                if(suggestion.isActive() || (notApproved && suggestion.isApproved())) continue ; 
                suggestions.add(suggestion) ;
            }
        }

        return suggestions ;
    }
}

package com.zafra.starterapp.handlers.capital;

import com.zafra.starterapp.models.capital.Book;
import com.zafra.starterapp.models.capital.CapitalActionData;
import java.util.Map;

public interface CapitalActionHandler {
   void handle(Map<String, Map<String, Book>> books, CapitalActionData data);
   void validateData(Map<String, Map<String, Book>> books, CapitalActionData data) throws Exception;
}

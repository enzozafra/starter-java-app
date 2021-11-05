package com.zafra.starterapp.handlers;

import org.springframework.stereotype.Component;

@Component
public class DependedHandler {

  public String handle() {
    return "Hi I'm the depended handler";
  }
}

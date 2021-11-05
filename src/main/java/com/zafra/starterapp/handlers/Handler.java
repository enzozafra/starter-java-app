package com.zafra.starterapp.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Handler {
  private DependedHandler dependedHandler;

  @Autowired
  public Handler(DependedHandler dependedHandler) {
    this.dependedHandler = dependedHandler;
  }

  public String handle() {
    return "Hi im the handler";
  }

  public String dependedHandlerMessage() {
    return dependedHandler.handle();
  }
}
package org.greensborough;

public interface Context<S, T>
{
  S execute( S context, T elem );
}

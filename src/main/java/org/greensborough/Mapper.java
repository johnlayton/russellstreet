package org.greensborough;

public interface Mapper<S, T>
{
  S execute( T elem );
}

package org.greensborough;

import java.util.List;

public interface Factory<T>
{
  List<T> create();
}

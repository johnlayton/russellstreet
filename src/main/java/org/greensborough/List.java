package org.greensborough;

import java.util.Map;

public interface List<T>
  extends java.util.List<T>
{

  T first();

  List<T> first( int count );

  T last();

  List<T> last( int count );

  T head();

  List<T> tail();

  List<T> with( T elem );

  List<T> plus( T elem );

  List<T> replace( int a, T elem );

  List<T> swap( int a, int b );

  List<T> copy();

  <S> S inject( S initial, Context<S, T> context );

  //<S> S inject( Context<S, T> context );

  <S> List<S> map( Mapper<S, T> mapper );

  <S> List<S> collect( Mapper<S, T> mapper );

  List<T> each( Closure<T> closure );

  <S> Map<S, List<T>> group( Mapper<S, T> mapper );

  String join( String token );

  List<List<T>> combination( int count );

  List<List<T>> combination( int count, Mapper<Boolean, T> mapper );

  List<List<T>> permutation( int count );

  List<List<T>> permutation( int count, Mapper<Boolean, T> mapper );

  /*
   List<T> cycle( Mapper<Boolean, T> mapper );

   List<T> compact();

   List<T> unique();

   List<T> drop( int count );

   List<T> flatten();

   List<T> sort( Comparator<T> comparator );

   List<T> select( Mapper<Boolean, T> mapper );

   List<T> reject( Mapper<Boolean, T> mapper );

   List<List<T>> repeatCombination( int count );

   List<List<T>> repeatPermutation( int count );

   List<List<T>> product( List<T>... lists );

   List<List<T>> zip( List<T>... lists );

   List<T> rotate( int count );

   String join( String token, Mapper<String, ? super T> mapper );
   */
}

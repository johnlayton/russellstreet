package org.greensborough;

import org.greensborough.Closure;
import org.greensborough.Context;
import org.greensborough.List;
import org.greensborough.Lists;
import org.greensborough.Mapper;

public enum TestEnum
{
  A, B, C, D, E, F, G, H, I, J, K, L, M,
  N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

  public static Closure<TestEnum> debug()
  {
    return new Closure<TestEnum>()
    {
      public TestEnum execute( TestEnum elem )
      {
        System.out.println( String.format( " Value -> %s ", elem ) );
        return elem;
      }
    };
  }

  public static Context<String, TestEnum> join( final String token )
  {
    return join( token, new Mapper<String, TestEnum>()
    {
      public String execute( TestEnum elem )
      {
        return elem.toString();
      }
    } );
  }

  public static Context<String, TestEnum> join( final String token,
                                                final Mapper<String, TestEnum> mapper )
  {
    return new Context<String, TestEnum>()
    {
      public String execute( String context, TestEnum value )
      {
        return context + token + mapper.execute( value );
      }
    };
  }

  public static Mapper<Integer, TestEnum> integer()
  {
    return new Mapper<Integer, TestEnum>()
    {
      public Integer execute( TestEnum value )
      {
        return new Integer( value.name().toCharArray()[ 0 ] );
      }
    };
  }

  public static Mapper<Integer, TestEnum> length()
  {
    return new Mapper<Integer, TestEnum>()
    {
      public Integer execute( TestEnum value )
      {
        return value.name().length();
      }
    };
  }

  public static void main( String[] args )
  {
    collections();
    accessors();
  }

  private static void collections()
  {
    final List<TestEnum> list = Lists.list( TestEnum.values() );

    println( "Inject", list.inject( "", TestEnum.join( " -> " ) ) );
    println( "Map", list.map( TestEnum.integer() ) );
    println( "Collect", list.collect( TestEnum.integer() ) );
    println( "Each", list.each( TestEnum.debug() ) );
    println( "Group", list.group( TestEnum.length() ) );
    println( "Join", list.join( "" ) );

    final Mapper<String, List<TestEnum>> mapper = new Mapper<String, List<TestEnum>>()
    {
      @Override
      public String execute( final List<TestEnum> elem )
      {
        return elem.join( "" );
      }
    };

    println( "Combinations", list.combination( 3 ).collect( mapper ) );
    println( "Permutations", list.permutation( 3 ).collect( mapper ) );
  }

  private static void accessors()
  {
    final List<TestEnum> list = Lists.list( TestEnum.values() );

    println( "First", list.first() );
    println( "First 2", list.first( 2 ) );
    println( "Last", list.last() );
    println( "Last 2", list.last( 2 ) );
    println( "Head", list.head() );
    println( "Tail", list.tail() );
  }

  private static void println( final String name,
                               final Object object )
  {
    System.out.println( name + ":");
    System.out.println( object );
    System.out.println( "================" );
  }

}

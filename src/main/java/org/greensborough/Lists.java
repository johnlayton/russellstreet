package org.greensborough;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

public final class Lists
{

  private Lists()
  {
  }

  public static <T> List<T> list( T... values )
  {
    return new DefaultList<T>( Arrays.asList( values ) );
  }

  private static class DefaultList<T>
    extends DelegatingList<T>
    implements List<T>
  {

    public DefaultList()
    {
      super();
    }

    public DefaultList( final T value )
    {
      super( value );
    }

    public DefaultList( final T... values )
    {
      super( values );
    }

    public DefaultList( final java.util.List<T> values )
    {
      super( values );
    }

    public DefaultList( final List<T> list )
    {
      super( list );
    }

    public DefaultList( final List<T> list, final T elem )
    {
      super( list, elem );
    }

    public T head()
    {
      return get( 0 );
    }

    public List<T> first( int count )
    {
      return new DefaultList<T>( subList( 0, count ) );
    }

    public T last()
    {
      return get( size() - 1 );
    }

    public List<T> last( int count )
    {
      return new DefaultList<T>( subList( size() - count, size() ) );
    }

    public T first()
    {
      return get( 0 );
    }

    public List<T> tail()
    {
      return new DefaultList<T>( subList( 1, size() ) );
    }

    public List<T> with( final T elem )
    {
      add( elem );
      return this;
    }

    public List<T> plus( final T elem )
    {
      return new DefaultList<T>( this, elem );
    }

    public List<T> copy()
    {
      return new DefaultList<T>( this );
    }

    @Override
    public List<T> replace( final int a, final T elem )
    {
      set( a, elem );
      return this;
    }

    @Override
    public List<T> swap( final int a, final int b )
    {
      return this.copy()
        .replace( a, get( b ) )
        .replace( b, get( a ) );
    }

    public <S> S inject( S initial, Context<S, T> context )
    {
      for ( final T value : this )
      {
        initial = context.execute( initial, value );
      }

      return initial;
    }

    /*
    public <S> S inject( Context<S, T> context )
    {
      return inject( head(), context );
    }
    */

    public List<T> each( Closure<T> closure )
    {
      return inject( new DefaultList<T>(), wrap( closure ) );
    }

    public <S> List<S> map( Mapper<S, T> mapper )
    {
      return inject( new DefaultList<S>(), wrap( mapper ) );
    }

    public <S> List<S> collect( Mapper<S, T> mapper )
    {
      return inject( new DefaultList<S>(), wrap( mapper ) );
    }

    public <S> Map<S, List<T>> group( final Mapper<S, T> mapper )
    {
      return inject( new HashMap<S, List<T>>(), new Context<Map<S, List<T>>, T>()
      {
        public Map<S, List<T>> execute( Map<S, List<T>> context, T elem )
        {
          if ( context.containsKey( mapper.execute( elem ) ) )
          {
            context.get( mapper.execute( elem ) ).add( elem );
          }
          else
          {
            context.put( mapper.execute( elem ), new DefaultList<T>().plus( elem ) );
          }
          return context;
        }
      } );
    }

    public String join( final String token )
    {
      return inject( "", new Context<String, T>()
      {
        public String execute( String context, T value )
        {
          return context + token + value.toString();
        }
      } );
    }

    public String join( final String token,
                        final Mapper<String, ? super T> mapper )
    {
      return inject( "", new Context<String, T>()
      {
        public String execute( String context, T value )
        {
          return context + token + mapper.execute( value );
        }
      } );
    }

    private <T> List<List<T>> combination( List<List<T>> context, List<T> head, List<T> rest, int count )
    {
      if ( !rest.isEmpty() )
      {
        final List<T> current = head.plus( rest.head() );

        if ( current.size() == count )
        {
          context.with( current );
        }
        else
        {
          combination( context, current, rest.tail(), count );
        }

        combination( context, head.copy(), rest.tail(), count );
      }
      return context;
    }

    public List<List<T>> combination( int count )
    {
      return combination( new DefaultList<List<T>>(), new DefaultList<T>(), this, count );
    }

    public List<List<T>> combination( int count, Mapper<Boolean, T> mapper )
    {
      return combination( new DefaultList<List<T>>(), new DefaultList<T>(), this, count );
    }

    private <T> List<List<T>> permutation( List<List<T>> context, List<T> list, int current, int count )
    {
      if ( count == 0 )
      {
        context.with( list.last( list.size() - current ) );
      }
      else
      {
        for ( int i = 0; i < current; i++ )
        {
          permutation( context, list.swap( i, current - 1 ), current - 1, count - 1 );
        }
      }
      return context;
    }

    public List<List<T>> permutation( int count )
    {
      return permutation( new DefaultList<List<T>>(), this, size(), count );
    }

    public List<List<T>> permutation( int count, Mapper<Boolean, T> mapper )
    {
      return permutation( new DefaultList<List<T>>(), this, size(), count );
    }

    private static <T> Context<List<T>, T> wrap( final Closure<T> closure )
    {
      return new Context<List<T>, T>()
      {
        public List<T> execute( List<T> context, T elem )
        {
          return context.plus( closure.execute( elem ) );
        }
      };
    }

    private static <S, T> Context<List<S>, T> wrap( final Mapper<S, T> mapper )
    {
      return new Context<List<S>, T>()
      {
        public List<S> execute( List<S> context, T elem )
        {
          return context.plus( mapper.execute( elem ) );
        }
      };
    }

  }

  private static abstract class DelegatingList<T>
    implements java.util.List<T>
  {
    private java.util.List<T> delegate;

    private Factory<T> factory = new Factory<T>()
    {
      @Override
      public java.util.List<T> create()
      {
        return new ArrayList<T>();
      }
    };

    public DelegatingList()
    {
      delegate = factory.create();
    }

    public DelegatingList( T value )
    {
      this();
      add( value );
    }

    public DelegatingList( T... values )
    {
      this();
      addAll( Arrays.asList( values ) );
    }

    public DelegatingList( java.util.List<T> values )
    {
      this();
      addAll( values );
    }

    public DelegatingList( final List<T> list )
    {
      this();
      addAll( list );
    }

    public DelegatingList( final List<T> list, final T elem )
    {
      this();
      addAll( list );
      add( elem );
    }

    @Override
    public int size()
    {
      return delegate.size();
    }

    @Override
    public boolean isEmpty()
    {
      return delegate.isEmpty();
    }

    @Override
    public boolean contains( final Object o )
    {
      return delegate.contains( o );
    }

    @Override
    public Iterator<T> iterator()
    {
      return delegate.iterator();
    }

    @Override
    public Object[] toArray()
    {
      return delegate.toArray();
    }

    @Override
    public <T> T[] toArray( final T[] a )
    {
      return delegate.toArray( a );
    }

    @Override
    public boolean add( final T t )
    {
      return delegate.add( t );
    }

    @Override
    public boolean remove( final Object o )
    {
      return delegate.remove( o );
    }

    @Override
    public boolean containsAll( final Collection<?> c )
    {
      return delegate.containsAll( c );
    }

    @Override
    public boolean addAll( final Collection<? extends T> c )
    {
      return delegate.addAll( c );
    }

    @Override
    public boolean addAll( final int index, final Collection<? extends T> c )
    {
      return delegate.addAll( index, c );
    }

    @Override
    public boolean removeAll( final Collection<?> c )
    {
      return delegate.removeAll( c );
    }

    @Override
    public boolean retainAll( final Collection<?> c )
    {
      return delegate.retainAll( c );
    }

    @Override
    public void clear()
    {
      delegate.clear();
    }

    @Override
    public boolean equals( final Object o )
    {
      return delegate.equals( o );
    }

    @Override
    public int hashCode()
    {
      return delegate.hashCode();
    }

    @Override
    public T get( final int index )
    {
      return delegate.get( index );
    }

    @Override
    public T set( final int index, final T element )
    {
      return delegate.set( index, element );
    }

    @Override
    public void add( final int index, final T element )
    {
      delegate.add( index, element );
    }

    @Override
    public T remove( final int index )
    {
      return delegate.remove( index );
    }

    @Override
    public int indexOf( final Object o )
    {
      return delegate.indexOf( o );
    }

    @Override
    public int lastIndexOf( final Object o )
    {
      return delegate.lastIndexOf( o );
    }

    @Override
    public ListIterator<T> listIterator()
    {
      return delegate.listIterator();
    }

    @Override
    public ListIterator<T> listIterator( final int index )
    {
      return delegate.listIterator( index );
    }

    @Override
    public java.util.List<T> subList( final int fromIndex, final int toIndex )
    {
      return delegate.subList( fromIndex, toIndex );
    }

    @Override
    public String toString()
    {
      return "DefaultList{ delegate = " + delegate + "}";
    }

  }

}
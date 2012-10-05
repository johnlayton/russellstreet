package org.greensborough;

import org.greensborough.List;
import org.greensborough.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ListsTest
{

  @Test
  public void testShouldReturnFirstStringWhenFirstCalled()
  {
    assertEquals( strings().first(), "A" );
  }

  @Test
  public void testShouldReturnFirstIntegerWhenFirstCalled()
  {
    assertEquals( integers().first(), new Integer( 1 ) );
  }

  @Test
  public void testShouldReturnStringWhenHeadCalled()
  {
    assertEquals( strings().head(), "A" );
  }

  @Test
  public void testShouldReturnFirstIntegerWhenHeadCalled()
  {
    assertEquals( integers().head(), new Integer( 1 ) );
  }

  @Test
  public void testShouldReturnStringWhenLastCalled()
  {
    assertEquals( strings().last(), "B" );
  }

  @Test
  public void testShouldReturnLastIntegerWhenLastCalled()
  {
    assertEquals( integers().last(), new Integer( 2 ) );
  }

  @Test
  public void testShouldReturnListOfStringWhenLastCalledWithCount()
  {
    assertEquals( strings().last( 1 ), Lists.list( "B" ) );
  }

  @Test
  public void testShouldReturnListOfIntegerWhenLastCalledWithCount()
  {
    assertEquals( integers().last( 1 ), Lists.list( 2 ) );
  }

  @Test
  public void testCombinations()
  {
    /*
      Number of Combinations nCr = n! / r! * (n - r)!
                                 = 5! / 3! * (5 - 3)!
                                 = 5! / 3! * 2!
                                 = 120 / 6 * 2
                                 = 10
     */
    assertEquals( Lists.list( TestEnum.values() ).first( 5 ).combination( 3 ).size(), 10 );
    /*
      Number of Combinations nCr = n! / r! * (n - r)!
                                 = 26! / 3! * (26 - 3)!
                                 = 26! / 3! * 23!
                                 = 2600
     */
    assertEquals( Lists.list( TestEnum.values() ).combination( 3 ).size(), 2600 );
  }

  @Test
  public void testPermutations()
  {
    /*
      Number of Combinations nPr = n! / (n - r)!
                                 = 5! / (5 - 3)!
                                 = 5! / 2!
                                 = 120 / 2
                                 = 60
     */
    assertEquals( Lists.list( TestEnum.values() ).first( 5 ).permutation( 3 ).size(), 60 );
    /*
      Number of Combinations nPr = n! / (n - r)!
                                 = 26! / (26 - 3)!
                                 = 26! / 23!
                                 = 15600
     */
    assertEquals( Lists.list( TestEnum.values() ).permutation( 3 ).size(), 15600 );
  }

  private <T> void assertEquals( T actual, T expected )
  {
    Assert.assertEquals( actual, expected );
  }

  private List<String> strings()
  {
    return Lists.list( "A", "B" );
  }

  private List<Integer> integers()
  {
    return Lists.list( 1, 2 );
  }

}

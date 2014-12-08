/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import truthtablegenerator.BinaryMath;

/**
 *
 * @author Bryce
 */
public class BinaryMathTest {
    
    public BinaryMathTest() {
    }
    
    @Test
    public void testNot() {
        System.out.println("Testing Integer Not");
        assertEquals(BinaryMath.not(1), 0);
        assertEquals(BinaryMath.not(0), 1);
        
        System.out.println("Testing Character Not");
        assertEquals(BinaryMath.not('0'), 1);
        assertEquals(BinaryMath.not('1'), 0);
        
        System.out.println("Testing String Not");
        assertEquals(BinaryMath.not("0"), 1);
        assertEquals(BinaryMath.not("1"), 0);
    }
    
    @Test
    public void testAnd() {
        System.out.println("Testing Integer And");
        assertEquals(BinaryMath.and(0, 0), 0);
        assertEquals(BinaryMath.and(0, 1), 0);
        assertEquals(BinaryMath.and(1, 0), 0);
        assertEquals(BinaryMath.and(1, 1), 1);
        
        System.out.println("Testing Character And");
        assertEquals(BinaryMath.and('0', '0'), 0);
        assertEquals(BinaryMath.and('0', '1'), 0);
        assertEquals(BinaryMath.and('1', '0'), 0);
        assertEquals(BinaryMath.and('1', '1'), 1);
        
        System.out.println("Testing String And");
        assertEquals(BinaryMath.and("0", "0"), 0);
        assertEquals(BinaryMath.and("0", "1"), 0);
        assertEquals(BinaryMath.and("1", "0"), 0);
        assertEquals(BinaryMath.and("1", "1"), 1);
    }
    
    @Test
    public void testOr() {
        System.out.println("Testing Integer Or");
        assertEquals(BinaryMath.or(0, 0), 0);
        assertEquals(BinaryMath.or(0, 1), 1);
        assertEquals(BinaryMath.or(1, 0), 1);
        assertEquals(BinaryMath.or(1, 1), 1);
        
        System.out.println("Testing Character Or");
        assertEquals(BinaryMath.or('0', '0'), 0);
        assertEquals(BinaryMath.or('0', '1'), 1);
        assertEquals(BinaryMath.or('1', '0'), 1);
        assertEquals(BinaryMath.or('1', '1'), 1);
        
        System.out.println("Testing String Or");
        assertEquals(BinaryMath.or("0", "0"), 0);
        assertEquals(BinaryMath.or("0", "1"), 1);
        assertEquals(BinaryMath.or("1", "0"), 1);
        assertEquals(BinaryMath.or("1", "1"), 1);
    }
    
    @Test
    public void testImplies() {
        System.out.println("Testing Integer Implies");
        assertEquals(BinaryMath.implies(0, 0), 1);
        assertEquals(BinaryMath.implies(0, 1), 1);
        assertEquals(BinaryMath.implies(1, 0), 0);
        assertEquals(BinaryMath.implies(1, 1), 1);
        
        System.out.println("Testing Character Implies");
        assertEquals(BinaryMath.implies('0', '0'), 1);
        assertEquals(BinaryMath.implies('0', '1'), 1);
        assertEquals(BinaryMath.implies('1', '0'), 0);
        assertEquals(BinaryMath.implies('1', '1'), 1);
        
        System.out.println("Testing String Implies");
        assertEquals(BinaryMath.implies("0", "0"), 1);
        assertEquals(BinaryMath.implies("0", "1"), 1);
        assertEquals(BinaryMath.implies("1", "0"), 0);
        assertEquals(BinaryMath.implies("1", "1"), 1);
    }
    
    @Test
    public void testIFF() {
        System.out.println("Testing Integer IFF");
        assertEquals(BinaryMath.iff(0, 0), 1);
        assertEquals(BinaryMath.iff(0, 1), 0);
        assertEquals(BinaryMath.iff(1, 0), 0);
        assertEquals(BinaryMath.iff(1, 1), 1);
        
        System.out.println("Testing Character IFF");
        assertEquals(BinaryMath.iff('0', '0'), 1);
        assertEquals(BinaryMath.iff('0', '1'), 0);
        assertEquals(BinaryMath.iff('1', '0'), 0);
        assertEquals(BinaryMath.iff('1', '1'), 1);
        
        
        System.out.println("Testing String IFF");
        assertEquals(BinaryMath.iff("0", "0"), 1);
        assertEquals(BinaryMath.iff("0", "1"), 0);
        assertEquals(BinaryMath.iff("1", "0"), 0);
        assertEquals(BinaryMath.iff("1", "1"), 1);
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}

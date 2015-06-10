import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Cale Gibson (͡° ͜ʖ͡°)
 * on 02/03/15.
 */
public class calculateNr {
    public static void main(String[] args) throws IOException
    {
        //Check Args to ensure usage is correct
        if(args.length != 1)
        {
            System.out.println("Argument count incorrect");
            System.out.println("[USAGE] calculateNr text.txt [USAGE]");
            System.exit(0);
        }

        //Get the text file
        String textFile = args[0];

        try{
            //Tree inits
            StringComparator stringComp = new StringComparator();
            AVLTree<String, Integer> tree = new AVLTree<String, Integer>(stringComp);

            //Read text into FileWordRead object
            FileWordRead words = new FileWordRead(textFile);
            Iterator<String> it = words.getIterator();
            //Iterate through each word
            while(it.hasNext())
            {
                String next = it.next();
                //If there is no key in tree, add it with a value of 1
                if(tree.find(next) == null)
                {
                   tree.insertNew(next, 1);
                }
                //Else modify the value but an increment of 1
                else
                {
                    DictEntry entry = tree.find(next);
                    int val = (Integer)entry.value();
                    val++;
                    tree.modifyValue(next, val);
                }
            }
            //Get iterator of tree and insert count into new tree
            Iterator<DictEntry<String,Integer> > iter = tree.inOrder( );
            IntegerComparator intComp = new IntegerComparator();
            AVLTree outputTree = new AVLTree<Integer, Integer>(intComp);
            while (iter.hasNext())
            {
                DictEntry<String,Integer> next = iter.next();
                if(outputTree.find(next.value()) == null)
                {
                    outputTree.insertNew(next.value(), 1);
                }
                else
                {

                    int val = (Integer)outputTree.find(next.value()).value();
                    val++;
                    outputTree.modifyValue(next.value(), val);
                }
            }

            //Print out the contents of the tree inorder order
            Iterator<DictEntry<Integer,Integer> > iterOutput = outputTree.inOrder( );
            System.out.println("The NLP of file " + textFile);
            System.out.println("**********************************");
            while (iterOutput.hasNext())
            {
                DictEntry<Integer,Integer> next = iterOutput.next();
                System.out.println("N(" + next.key() + ") = " + next.value());
            }
            System.out.println("**********************************");

        }catch (AVLTreeException e)
        {
            System.out.println("AVLTreeException: " + e.getMessage());
        }
    }
}
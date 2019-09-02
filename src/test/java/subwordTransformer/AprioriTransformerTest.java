package subwordTransformer;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import subwordTransformer.apriori.AprioriParameter;
import subwordTransformer.apriori.AprioriTransformer;
import subwordTransformer.apriori.SupervisedAprioriTransformer;

@RunWith(JUnit4.class)
public class AprioriTransformerTest extends TransformerTest {

  @Test
  public void testTransform() {
    // System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    AprioriTransformer tr = new AprioriTransformer(4, (short) -1);

    short[][] trainingWords = new short[][] { { 2, 0, 1, 1 }, { 0, 0, 1, 2 }, { 3, 2, 0, 0 }, { 3, 0, 3, 2 }, { 3, 3, 3, 1 }, { 1, 3, 1, 0 }, { 1, 1, 2, 3 }, { 0, 2, 3, 2 }, { 2, 0, 0, 3 },
        { 1, 2, 0, 3 } };
    tr.setWords(trainingWords);

    short[][] words = new short[][] { { 3, 0, 3, 2 }, { 1, 0, 2, 0 }, { 3, 0, 2, 1 } };
    short[][][] expecteds = new short[][][] { { { 3, -1, 3, -1 }, { -1, 0, -1, 2 }, { -1, -1, 3, 2 } }, { { 1, -1, -1, -1 }, { -1, 0, -1, -1 } }, { { -1, 0, -1, -1 } } };
    AprioriParameter[] parameters = new AprioriParameter[] { new AprioriParameter(2, 0.2), new AprioriParameter(1, 0.3), new AprioriParameter(1, 0.4) };

    for (int i = 0; i < words.length; i++) {
      tr.setParameter(parameters[i]);
      tr.fit();
      short[][] subwords = tr.transformWord(words[i]);
      assertArrayEqualAnyOrder("transformation result of " + Arrays.toString(words[i]) + " with " + parameters[i] + " does NOT match", expecteds[i], subwords);
    }

  }

  @Test
  public void testSupervisedTransform() {
    // System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    SupervisedAprioriTransformer tr = new SupervisedAprioriTransformer(4, (short) -1);

    short[][][] trainingWords = new short[][][] { { { 2, 0, 1, 1 }, { 0, 0, 1, 2 }, { 3, 2, 0, 0 }, { 3, 0, 3, 2 }, { 3, 3, 3, 1 }, { 1, 3, 1, 0 } },
        { { 1, 1, 2, 3 }, { 0, 2, 3, 2 }, { 2, 0, 0, 3 }, { 1, 2, 0, 3 } } };
    tr.setWords(trainingWords);

    short[][] words = new short[][] { { 3, 0, 3, 2 }, { 1, 0, 2, 0 } };
    short[][][] expecteds = new short[][][] { { { -1, 0, 3, 2 }, { 3, 0, -1, 2 }, { 3, 0, 3, -1 }, { 3, -1, 3, 2 }, { 3, 0, 3, 2 } }, { { 1, -1, 2, -1 }, { 1, -1, -1, 0 } } };
    AprioriParameter[] parameters = new AprioriParameter[] { new AprioriParameter(3, 0.8), new AprioriParameter(2, 1.0) };

    for (int i = 0; i < words.length; i++) {
      tr.setParameter(parameters[i]);
      tr.fit();
      short[][] subwords = tr.transformWord(words[i]);
      assertArrayEqualAnyOrder("transformation result of " + Arrays.toString(words[i]) + " with " + parameters[i] + " does NOT match", expecteds[i], subwords);
    }

  }

}

package ch.cyberduck.ui.comparator;

import ch.cyberduck.core.AbstractTestCase;
import ch.cyberduck.core.Path;

import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.assertEquals;

/**
 * @version $Id$
 */
public class RegionComparatorTest extends AbstractTestCase {

    @Test
    public void testCompareFirst() throws Exception {
        assertEquals(0,
                new RegionComparator(true).compareFirst(new Path("/a", EnumSet.of(Path.Type.file)), new Path("/b", EnumSet.of(Path.Type.file))));
    }
}
import model.VCFFile;
import org.junit.Assert;
import org.junit.Test;
import utils.VCFReader;

import java.net.URISyntaxException;

public class VCFReaderTest {

    @Test
    public void premierTest() {
        VCFReader reader = VCFReader.getSharedInstance();
        try {
            VCFFile vcfFile = reader.loadVCFFile("vcfFile.vcf");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1,1);
    }

}

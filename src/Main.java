import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
// 1.io是面向流的，也就是读取数据的时候是从流上逐个读取，所以数据不能进行整体以为，没有缓冲区;nio是面向缓冲区的，数据是存储在缓冲区中，读取数据是在缓冲区中进行，所以进行数据的偏移操作更加方便
// 2，io是阻塞的，当一个线程操作io时如果当前没有数据可读，那么线程阻塞，nio由于是对通道操作io，所以是非阻塞，当一个通道无数据可读，可切换通道处理其他io
// 3，nio有selecter选择器，就是线程通过选择器可以选择多个通道，而io只能处理一个

// channel都要读到buffer中再进行操作
// 对于buffer中也需要读到channel中再进行输出
public class Main {

    public static void main(String[] args){
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = 0;
        try {
            bytesRead = inChannel.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }

            buf.clear();
            try {
                bytesRead = inChannel.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            aFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

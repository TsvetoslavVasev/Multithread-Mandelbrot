package com.vasev.ceco;

/**
 * Created by vasev on 22/05/19.
 */
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Fractal {

    private int width;

    private int height;

    private int threadNumber;

    private static String filename = "mandelbrot.png";



    public Fractal(int _width, int _height, int _threadNumber)
    {
        width = _width;
        height = _height;
        threadNumber = _threadNumber;
    }



    //the fractal generate function
    void generate()
    {
        Timer timer = new Timer();
        timer.start();

        BufferedImage fractalImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        int maximum = 800;


        int[] plate = new int[maximum];

        for(int i = 0; i < maximum; ++i)
        {
            plate[i] = Color.HSBtoRGB(i/256.0f, 1, i/(i+8.0f));
        }

        int peace = height / threadNumber;

        int leftover = this.height % peace;
        Thread[] threads = new Thread[threadNumber];

        for(int i = 0; i < threadNumber; ++i)
        {
            int start = i * peace;
            int end = (i + 1) * peace;

            if(i == threadNumber - 1) {
                start += leftover;
            }

            FractalRunnable fractal = new FractalRunnable(plate ,fractalImage, start, end, i+1);
            threads[i] = new Thread(fractal);
            threads[i].start();
        }

        for(int i = 0; i < threadNumber; ++i)
        {
            try
            {
                threads[i].join();
            }
            catch(Exception e)
            {
                System.out.println("Error when joining threads: " + e);
            }
        }

        try
        {
            ImageIO.write(fractalImage, "png", new File(filename));
        }
        catch(Exception e)
        {
            System.out.println("Error when writing to file : " + e);
        }

        System.out.println("Execution time: " + timer.stop() + " millis for threads: " + threadNumber);
    }
}
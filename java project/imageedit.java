import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class imageedit {

    //1 rotate clockwise
    static BufferedImage rotateClockwise(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color pixel = new Color(inputImage.getRGB(i,j));
                outputImage.setRGB(j, i, pixel.getRGB());
            }
        }

        outputImage = mirror(outputImage);

        return outputImage;
    }

    //2 rotate anticlockwise
    static BufferedImage rotateAntiClockwise(BufferedImage inputImage){
        BufferedImage outputImage = rotateClockwise(inputImage);
        outputImage = rotateClockwise(outputImage);
        outputImage = rotateClockwise(outputImage);
        return outputImage;
    }

    //3 mirror
    static BufferedImage mirror(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width/2; j++){
                Color pixel = new Color(inputImage.getRGB(j, i));
                outputImage.setRGB(j,i,inputImage.getRGB(inputImage.getWidth()-1-j , i));
                outputImage.setRGB(inputImage.getWidth()-1-j , i , pixel.getRGB());
                
            }
        }
        return outputImage;
    }

    //4 greyscale
    static BufferedImage convertToGreyScale(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_BYTE_GRAY);

        for(int i = 0; i < height; i++){

            for(int j =0; j < width; j++){

                outputImage.setRGB(j,i, inputImage.getRGB(j,i));

            }
        }
        return outputImage;
    }

    //5 change brightness
    static BufferedImage changeBrightness(BufferedImage inputImage , int increase){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_3BYTE_BGR);
        
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){

                Color pixel = new Color(inputImage.getRGB(j,i));

                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen(); 
                
                red = red + (increase*red/100);
                blue = blue + (increase*blue/100);
                green = green + (increase*green/100);

                if(red > 255){red = 255;}

                if(blue > 255){ blue = 255;}
                
                if(green > 255){ green = 255;}

                if(red < 0){ red = 0;}

                if(blue < 0){blue = 0;}

                if(green < 0){ green = 0; }

                Color newPixel = new Color(red , green , blue);

                outputImage.setRGB(j,i,newPixel.getRGB());


            }
        }

        return outputImage;
    }

    //6 blurr
    static BufferedImage blurr(BufferedImage inputImage , int pixelCount){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        int rowStart = 0;
        int rowEnd = pixelCount-1;

        while(rowEnd<height){

            int columnStart = 0;
            int columnEnd = pixelCount-1 ;

            while(columnEnd<width){

                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){

                        Color pixel = new Color(inputImage.getRGB(j,i));

                        sumRed += pixel.getRed();
                        sumBlue += pixel.getBlue();
                        sumGreen += pixel.getGreen();

                    }
                }

                int avgRed = sumRed/(pixelCount*pixelCount);
                int avgBlue = sumBlue/(pixelCount*pixelCount);
                int avgGreen = sumGreen/(pixelCount*pixelCount);

                Color newPixel = new Color(avgRed , avgGreen , avgBlue);

                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){
                        outputImage.setRGB(j , i , newPixel.getRGB() );
                    }
                }

                columnStart+=pixelCount;
                columnEnd+=pixelCount;
            }

            rowStart+=pixelCount;
            rowEnd+=pixelCount;
        }

        return outputImage;
    }

    public static void main (String agrs[]){

        Scanner sc = new Scanner(System.in);

        while (true){

            System.out.println("Shreyas's IMAGE EDITOR IN JAVA");

            System.out.println("");

            System.out.println("enter the path of the image you want to edited");

            

            String location = sc.next();

            File inputFile = new File(location);            

            System.out.println("1. Rotate your input image clockwise");

            System.out.println("2. Rotate your input image anticlockwise");

            System.out.println("3. Mirror your image");

            System.out.println("4. Convert your image to greyscale");

            System.out.println("5. Change the brightness of your image");

            System.out.println("6. Blurr your image");

            System.out.println("");

            System.out.print("enter the edit you want to perform :) ");

            int choice = sc.nextInt();

            try{

                BufferedImage inputImage = ImageIO.read(inputFile);

                switch(choice){

                    case 1: BufferedImage rotatedClockwise = rotateClockwise(inputImage);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname1 = sc.next();
                    File rotatedClockwiseImage = new File(outputname1 + ".jpeg");
                    ImageIO.write(rotatedClockwise , "jpeg" , rotatedClockwiseImage);
                    break;

                    case 2: BufferedImage rotatedAntiClockwise = rotateAntiClockwise(inputImage);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname2 = sc.next();
                    File rotatedAntiClockwiseImage = new File(outputname2 + ".jpeg");
                    ImageIO.write(rotatedAntiClockwise , "jpeg" , rotatedAntiClockwiseImage);
                    break;

                    case 3: BufferedImage mirrored = mirror(inputImage);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname3 = sc.next();
                    File mirroredImage = new File(outputname3 + ".jpeg");
                    ImageIO.write(mirrored , "jpeg" , mirroredImage);
                    break;

                    case 4: BufferedImage grayScale = convertToGreyScale(inputImage);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname4 = sc.next();
                    File grayScaleImage = new File(outputname4 + ".jpeg");
                    ImageIO.write(grayScale , "jpeg" , grayScaleImage);
                    break;

                    case 5: System.out.print("change brightness by what percentage: ");
                    int factor = sc.nextInt();
                    BufferedImage changedBrightness = changeBrightness(inputImage , factor);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname5 = sc.next();
                    File changedBrightnessImage = new File(outputname5 + ".jpeg");
                    ImageIO.write(changedBrightness , "jpeg" , changedBrightnessImage);
                    break;

                    case 6: System.out.print("enter length of pixel square: ");
                    int sideLength = sc.nextInt();
                    BufferedImage blurred = blurr(inputImage , sideLength);
                    System.out.print("Please enter the name of the output file: ");
                    String outputname7 = sc.next();
                    File blurredImage = new File(outputname7 + ".jpeg");
                    ImageIO.write(blurred , "jpeg" , blurredImage);
                    break;

                    default: System.out.println("\nPlease enter a valid option.\n");
                }
                
                
                }
                catch(IOException e){
                e.printStackTrace();
            }

        

        }
    }
}
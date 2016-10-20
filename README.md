# EECS410 Mobile Health

## A Smile-Level Indicator App

A App that lets user grades the smile levels of a series of images. The result can be used for data analysis purpose (data mining & machine learning)

The output will be log into a CSV file under the image folder with the name of the image folder.

## Usage

1. build and run **ImageLearning**.

2. When first running, it will ask permission for read SD card (for Android 6.0 and higher).

3. When App starts, it will lets you select a folder that contains images you want to grade.

4. Grade smile level of each image, note that next image will be load automatically.

5. You can use Previous and Next button to navigate between images, you will see your old grade.

6. When finish grading, use **Open Learning Image** to grade next folder or use **Open Result CSV** to see the stored result.

7. If the folder you open contains such a CSV file, it will load the old grade from that CSV file.

8. User can take a picture by their own and grade that picture by click on the camera button on the right upper corner.

## Sensor data

Data from sensors that are available on the smartphone will be record for further data analysis, including:

```
    ACCELEROMETER_X
    
    ACCELEROMETER_Y
    
    ACCELEROMETER_Z
    
    MAGNETIC_FIELD_X
    
    MAGNETIC_FIELD_Y
    
    MAGNETIC_FIELD_Z
    
    GYROSCOPE_X
    
    GYROSCOPE_Y
    
    GYROSCOPE_Z
    
    ROTATION_VECTOR_X
    
    ROTATION_VECTOR_Y
    
    ROTATION_VECTOR_Z
    
    LINEAR_ACCELERATION_X
    
    LINEAR_ACCELERATION_Y
    
    LINEAR_ACCELERATION_Z
    
    GRAVITY_X
    
    GRAVITY_Y
    
    GRAVITY_Z
    
    POSITION_X
    
    POSITION_Y
    
    VELOCITY_X
    
    VELOCITY_Y
    
    PRESSURE
    
    SIZE
```
## Collaborators

(In alphabet order)

Junqi Ma

Shipei Tian



This is a MockSDK to mimic BLE SDK Data for the below parameters 

        SPEED,
        SOC,
        CHARGE,
        RIGHT_IND,
        LEFT_IND,
        RIDE_MODE,
        ODO,
        HIGH_B,
        LOW_B,
        HAZARD,
        STAND


  Default Value, That will be emitted always with the static frequency 

      SPEED - 500 milliseconds 
      SOC - 5000 milliseconds 
      ODO - 5000 milliseconds 

  Query and Get Parameters, User has to enable using specific fucntion to get the Value. 

        RIGHT_IND, - 5000 milliseconds - Default Value is false 
        LEFT_IND,  - 5000 milliseconds - Default Value is false 
        RIDE_MODE, - 5000 milliseconds - Default Value is false 
        HIGH_B,    - 5000 milliseconds - Default Value is false 
        LOW_B,     - 5000 milliseconds - Default Value is false 
        HAZARD,    - 5000 milliseconds - Default Value is false 
        STAND      - 5000 milliseconds - Default Value is false 


How to Integrate -  

1) Get the aar file and Integarte it to your project. ( Download from the Project - MockSDK-debug.aar ) 
2) Gradle Implementation was already added as a sample Application
3) Add (@InternalCoroutinesApi) annotation in the top of the class where you are implementing this library


Sample Code : 
----------------


         EnfieldSDK.INSTANCE.getBleObject(new EnfieldSDK.enfieldCallback() {
                    @Override
                    public void onResult(@NonNull Map<EnfieldSDK.KEY, String> result) {


                //getting Speed value - Since Speed is default you need not to enable any
                if(result.get(EnfieldSDK.KEY.SPEED) != null ){
                    runOnUiThread(() -> {
                        speed.setText(result.get(EnfieldSDK.KEY.SPEED));
                    });
                }

                //NOTE : You can get the value of different field by changing the KEY 
                //NOTE : Check for null before Using 


            }
        });


         //Enable Side Stand 
        EnfieldSDK.INSTANCE.enableSideStand(true );
        
        //Disable Side Stand 
        EnfieldSDK.INSTANCE.enableSideStand(false  );






<?php
if(!empty($_POST['email']) && !empty($_POST['apikey'])){
    $email=$_POST['email'];
    $apikey=$_POST['apikey'];

    $con = mysqli_connect(hostname:"localhost" , username:"root" , password:"", database:"ecommerce");

    if($con){
        $sql="select * from appusers where email='".$email."' and apikey='".$apikey."'";
        $res=mysqli_query($con,$sql);

        if(mysqli_num_rows($res)!=0){
            $row=mysqli_fetch_assoc($res);
            $sqlUpdate="update appusers set apikey ='' where email='".$email."'";
            if(mysqli_query($con,$sqlUpdate)){
                echo "success";
            }else echo "logout failed";
        }else echo "success";
    }else echo "database connection failed";
}else echo "all fields are required";


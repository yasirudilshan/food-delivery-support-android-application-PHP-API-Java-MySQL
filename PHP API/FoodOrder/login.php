<?php
if(!empty($_POST['email']) && !empty($_POST['password'])){
    $email=$_POST['email'];
    $password=$_POST['password'];
    $result=array();

    $con = mysqli_connect(hostname:"localhost" , username:"root" , password:"", database:"ecommerce");

    if($con){
            $sql="select * from appusers where email='".$email."'";
            $res=mysqli_query($con,$sql);

            if(mysqli_num_rows($res)!=0){
                $row=mysqli_fetch_assoc($res);
                if($email==$row['email'] && password_verify($password,$row['password'])){
                    try{
                        $apikey=bin2hex(random_bytes(length:23));
                    }
                    catch(Exception $e){
                        $apikey=bin2hex(uniqid($email,more_entropy:true));
                    }
                    $sqlUpdate="update appusers set apikey='".$apikey."' where email='".$email."'";
                    if(mysqli_query($con,$sqlUpdate)){

                        $result=array("status"=>"success","message"=>"Login successful","name"=>$row['name'],"email"=>$row['email'],"apikey" => $apikey);

                    }
                    else $result=array("status"=>"failed","message"=>"login failed and try again");
                }
                else $result=array("status"=>"failed","message"=>"Retry with correct email and password");
            }
            else $result=array("status"=>"failed","message"=>"Retry with correct email and password");
    }
    else $result=array("status"=>"failed","message"=>"Database connection failed");
}
else $result=array("status"=>"failed","message"=>"All fields are required");

echo json_encode($result,flags:JSON_PRETTY_PRINT);
//echo $result;
# Azure Blob Storage Connectivity using Boomi

## Introduction
Azure Blob Storage is a cloud-based platform optimized for storing unstructured data, such as text or binary data. The stored file is called Blob and the directory where it’s stored is called Container.

The objective of this document is to show the generation of signature using Groovy script for HTTP requests’ Authorization header, to perform List, Get, Create and Delete operations in Azure Blob Storage.


## Azure Blob Storage Authorization with Shared Key
The storage account name, storage account access key, and blob container name are required to perform communication/transaction between Boomi and Azure Blob Storage. The storage account name and access keys can be found by going to the Storage Account.

**Step-1:** Once you are in the desired storage account, go to Access keys.

**Step-2:** Click on Storage account name to get the storage account name.

**Step-3:** Click on Show keys.

**Step-4:** Copy Key 1. 

![image](https://user-images.githubusercontent.com/12267939/177512848-ddeb89ae-07ed-4858-8855-10b7bd0bcd9d.png)

Container Name and Blob Name can be found in “Overview” section in the Storage Account.

![image](https://user-images.githubusercontent.com/12267939/177513430-d79738b1-ef53-4e13-af00-12abaf64a1ff.png)


## Implement CREATE Operation
Overall Boomi process to create blob in Azure Blob Storage is as below.
![image](https://user-images.githubusercontent.com/12267939/177513508-0cf79754-e3ed-496b-95f0-9e7b63cf6bf8.png)

Sub-process to create blobs in Azure Blob Storage is as below.
![image](https://user-images.githubusercontent.com/12267939/177513543-517ba5f5-883d-4b8d-a1b7-129ca8e19cb4.png)

**Step-1:** Set blob content.

![image](https://user-images.githubusercontent.com/12267939/177513593-2cc9ae38-05ce-4f59-a221-f29764e3c4b2.png)


**Step-2:** Set properties for blob creation.

![image](https://user-images.githubusercontent.com/12267939/177513765-53ea0697-2f1d-402d-b252-b1a0fccabe30.png)

Account Name DPP: Name of Azure Blob Storage account

Access Key DPP: Value of Access Key obtained from Azure Blob Storage in Base64 encoded format

Container Name DPP: Name of Azure Blob Storage container where the blob need to be created

HTTP Verb DPP: PUT

Container Name DDP: Container Name DPP
  
Blob Name DDP: Name of the blob to be created

Content Type DPP: The required Content-Type of the blob, for example, text/plan, text/csv, etc.

Operation DPP: CREATE


**Step-3:** Generate signature to perform CREATE operation to create blob.
  
![image](https://user-images.githubusercontent.com/12267939/177514002-7fa23b3a-f4bd-45cf-90d7-eece60c02f49.png)

  
**Step-4:** Create blob in Azure Blob Storage.

![image](https://user-images.githubusercontent.com/12267939/177514191-271f7a32-08a8-4458-864e-0134e0cd9529.png)

![image](https://user-images.githubusercontent.com/12267939/177514218-e1d12386-6be9-40ee-b73f-5d15bcd2536e.png)

![image](https://user-images.githubusercontent.com/12267939/177514244-c4c0116d-28ad-4a67-bcf4-b39cdc711b4e.png)


**Request Headers:**
  
Authorization: Value obtained from Step-3

x-ms-date: Value obtained from Step-3

x-ms-version: 2020-10-02

x-ms-blob-type: BlockBlob

**Resource Path:**

Container Name DDP: <alue obtained from Step-2

/

Blob Name DDP: Value obtained from Step-2

**Content Type:** text/csv


## Implement LIST Operation
Overall Boomi process to list blobs from container in Azure Blob Storage is as below.
  
![image](https://user-images.githubusercontent.com/12267939/177516185-4a4be21c-820c-4303-962a-de50dba53adc.png)


Sub-process to list blobs from container in Azure Blob Storage is as below.

![image](https://user-images.githubusercontent.com/12267939/177516228-fb8b5209-6298-4b35-9c09-586844e811fc.png)


**Step-1:** Set properties to list blobs from container.

![image](https://user-images.githubusercontent.com/12267939/177516303-365b0886-4e14-4d41-849c-e3c6680f0614.png)

Account Name DPP: Name of Azure Blob Storage account

Access Key DPP: Value of Access Key obtained from Azure Blob Storage in Base64 encoded format

Container Name DPP: Name of Azure Blob Storage container where the blob need to be created
                          
HTTP Verb DPP: GET
                          
Container Name DDP: Container Name DPP
                          
Operation DPP: LIST


**Step-2:** Generate signature to perform LIST operation to get a list of all the blobs present in a specified container.

![image](https://user-images.githubusercontent.com/12267939/177516377-1f92f0c0-3a1d-4b85-ad4a-389ed66ca0c2.png)


**Step-3:** List blobs from container in Azure Blob Storage.

![image](https://user-images.githubusercontent.com/12267939/177516698-34bf4fdf-3362-4f29-8a6d-5d0689b73a1b.png)

**Request Headers:**

Authorization: Value obtained from Step-2

x-ms-date: Value obtained from Step-2

x-ms-version: 2020-10-02

**Resource Path:**

Container Name DDP: Value obtained from Step-1

?restype=container&comp=list


## Implement GET Operation

Overall Boomi process to get the blob content from container in Azure Blob Storage is as below.

![image](https://user-images.githubusercontent.com/12267939/177521102-330fa8d0-1a35-41dc-b6c9-7b72e29f3d08.png)


Sub-process to get the blob content from container in Azure Blob Storage is as below.

![image](https://user-images.githubusercontent.com/12267939/177521160-e7e764fe-1374-4a28-b657-cead3d2f5779.png)


**Step-1:** Split data based on “Blob” element from XML returned by List operation.

![image](https://user-images.githubusercontent.com/12267939/177521302-845b90d5-ffb9-4c67-ab69-7eaf1742f0e0.png)


**XML File:** Please refer "Azure Blob List XML.xml" file.


**Note:** If the blob name(s) in Azure contains space(s) in between words, for example, “Boomi Azure Blob Storage Demo.csv”, then replace space(s) with %20, as per below example.You may also use URL Encoding script to have special/encoded characters in file name.

![image](https://user-images.githubusercontent.com/12267939/177521458-ac7ab562-0c5e-4e34-a2a2-5ccd4a25bca6.png)


**Step-2:** Set properties to get blobs.

![image](https://user-images.githubusercontent.com/12267939/177521503-c175b148-e146-4f21-aabe-30138e7fcbe1.png)

Account Name DPP: Name of Azure Blob Storage account

Access Key DPP: Value of Access Key obtained from Azure Blob Storage in Base64 encoded format

Container Name DPP: Name of Azure Blob Storage container where the blob need to be created

HTTP Verb DPP: GET

Container Name DDP: Container Name DPP

Blob Name DDP: Name obtained from the XML profile>

Operation DPP: GET


**Step-3:** Generate signature to perform GET operation to get the blob content.

![image](https://user-images.githubusercontent.com/12267939/177522346-669406eb-75ed-49fb-80bc-8bc72320cd02.png)


**Step-4:** Get blob content.

![image](https://user-images.githubusercontent.com/12267939/177522383-ddd48bfe-39f9-47f6-bc73-8e91dedb8790.png)

![image](https://user-images.githubusercontent.com/12267939/177522396-2727deb5-0e56-4130-becf-bfb92bbb1f1d.png)

![image](https://user-images.githubusercontent.com/12267939/177522417-b930b623-3558-4992-8c69-f288fe9def8a.png)

**Request Headers:**

Authorization: Value obtained from Step-3

x-ms-date: Value obtained from Step-3

x-ms-version: 2020-10-02

**Resource Path:**

Container Name DDP: Value obtained from Step-2

/

Blob Name DDP: Value obtained from Step-2


## Implement DELETE Operation

Overall Boomi process to delete blob from Azure Blob Storage is as below.
![image](https://user-images.githubusercontent.com/12267939/177522598-a78e05b2-be12-4f01-ace8-c738f7ab1f95.png)

Sub-process to delete blob from Azure Blob Storage is as below.

![image](https://user-images.githubusercontent.com/12267939/177522621-b44e846c-4fc1-4243-baf8-2895daa04307.png)

**Step-1:** Set properties for blob deletion.

![image](https://user-images.githubusercontent.com/12267939/177522663-5711f32e-6598-476a-9266-25f737a7a455.png)

Account Name DPP: Name of Azure Blob Storage account

Access Key DPP: Value of Access Key obtained from Azure Blob Storage in Base64 encoded format

Container Name DPP: Name of Azure Blob Storage container where the blob need to be created

HTTP Verb DPP: DELETE

Container Name DDP: Container Name DPP

Operation DPP: DELETE


**Step-2:** Put a Message shape with no data, to remove any content before generating signature. This step is essential if there’s any incoming data to the payload before signature generation, to avoid error in the generated signature.

![image](https://user-images.githubusercontent.com/12267939/177522760-20c313c0-5706-4438-aed8-dc57eb8bc1b7.png)


**Step-3:** Generate signature to perform DELETE operation.

![image](https://user-images.githubusercontent.com/12267939/177522794-afbc6fe3-edd2-4276-a77e-64a9eea3a81c.png)


**Step-4:** Delete blob from Azure Blob Storage.

![image](https://user-images.githubusercontent.com/12267939/177522810-c51620dc-0159-4560-b379-e7ffcbe37532.png)

![image](https://user-images.githubusercontent.com/12267939/177522833-ad303b86-935f-4d51-8c14-41f484300882.png)

![image](https://user-images.githubusercontent.com/12267939/177522847-91c1188d-5907-49e8-93a2-8675324f6556.png)

**Request Headers:**

Authorization: Value obtained from Step-3

x-ms-date: Value obtained from Step-3

x-ms-version: 2020-10-02

**Resource Path:**

Container Name DDP: Value obtained from Step-1

/

Blob Name DDP: Value obtained from GET operation’s Set Property

**Content Type:** null.

## Process Script

**Language:** Groovy 2.4

**Code:** Please refer "Generate Azure Blob Signature.groovy" file.


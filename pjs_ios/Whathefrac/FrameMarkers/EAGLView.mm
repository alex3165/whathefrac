/*==============================================================================
Copyright (c) 2010-2012 QUALCOMM Austria Research Center GmbH .
All Rights Reserved.
Qualcomm Confidential and Proprietary
==============================================================================*/


#import "EAGLView.h"
#import "Texture.h"
#import "QCARutils.h"
#import "A_object.h"
#import "C_object.h"
#import "Q_object.h"
#import "R_object.h"

#import <QCAR/Renderer.h>
#import <QCAR/VideoBackgroundConfig.h>
#import <QCAR/Marker.h>
#import <QCAR/MarkerResult.h>
#import "FrameMarkersAppDelegate.h"

// OpenGL 2
#import "ShaderUtils.h"
#define MAKESTRING(x) #x


namespace {
    // Letter object scale factor and translation
    const float kLetterScale = 25.0f;
    const float kLetterTranslate = 25.0f;
    
    // Texture filenames
    const char* textureFilenames[] = {
        "letter_Q.png",
        "letter_C.png",
        "letter_A.png",
        "letter_R.png"
    };    
}


@interface EAGLView (PrivateMethods)
@end

#define appDelegate (FrameMarkersAppDelegate *)[[UIApplication sharedApplication] delegate]

@implementation EAGLView


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    
	if (self)
    {
        // create list of textures we want loading - ARViewController will do this for us
        int nTextures = sizeof(textureFilenames) / sizeof(textureFilenames[0]);
        for (int i = 0; i < nTextures; ++i)
            [textureList addObject: [NSString stringWithUTF8String:textureFilenames[i]]];
    }
    return self;
}


- (void) add3DObjectWith:(int)numVertices ofVertices:(const float *)vertices normals:(const float *)normals texcoords:(const float *)texCoords with:(int)numIndices ofIndices:(const unsigned short *)indices usingTextureIndex:(NSInteger)textureIndex
{
    Object3D *obj3D = [[Object3D alloc] init];
    
    obj3D.numVertices = numVertices;
    obj3D.vertices = vertices;
    obj3D.normals = normals;
    obj3D.texCoords = texCoords;
    
    obj3D.numIndices = numIndices;
    obj3D.indices = indices;
    
    obj3D.texture = [textures objectAtIndex:textureIndex];
    
    [objects3D addObject:obj3D];
    [obj3D release];    
}

- (void) setup3dObjects
{
    // build the array of objects we want drawn and their texture
    // in this example we have 4 textures and 4 objects - Q, C, A, R
        
    [self add3DObjectWith:NUM_Q_OBJECT_VERTEX ofVertices:QobjectVertices normals:QobjectNormals texcoords:QobjectTexCoords
                     with:NUM_Q_OBJECT_INDEX ofIndices:QobjectIndices usingTextureIndex:0];

    [self add3DObjectWith:NUM_C_OBJECT_VERTEX ofVertices:CobjectVertices normals:CobjectNormals texcoords:CobjectTexCoords
                     with:NUM_C_OBJECT_INDEX ofIndices:CobjectIndices usingTextureIndex:1];
    
    [self add3DObjectWith:NUM_A_OBJECT_VERTEX ofVertices:AobjectVertices normals:AobjectNormals texcoords:AobjectTexCoords
                     with:NUM_A_OBJECT_INDEX ofIndices:AobjectIndices usingTextureIndex:2];

    [self add3DObjectWith:NUM_R_OBJECT_VERTEX ofVertices:RobjectVertices normals:RobjectNormals texcoords:RobjectTexCoords
                     with:NUM_R_OBJECT_INDEX ofIndices:RobjectIndices usingTextureIndex:3];
}


////////////////////////////////////////////////////////////////////////////////
// Draw the current frame using OpenGL
//
// This method is called by QCAR when it wishes to render the current frame to
// the screen.
//
// *** QCAR will call this method on a single background thread ***
- (void)renderFrameQCAR
{
    if (APPSTATUS_CAMERA_RUNNING == qUtils.appStatus) {
        [self setFramebuffer];
        
        // Clear colour and depth buffers
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // Retrieve tracking state and render video background and 
        QCAR::State state = QCAR::Renderer::getInstance().begin();
        QCAR::Renderer::getInstance().drawVideoBackground();
        
        glEnable(GL_DEPTH_TEST);
        // We must detect if background reflection is active and adjust the culling direction. 
        // If the reflection is active, this means the pose matrix has been reflected as well,
        // therefore standard counter clockwise face culling will result in "inside out" models. 
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        if(QCAR::Renderer::getInstance().getVideoBackgroundConfig().mReflection == QCAR::VIDEO_BACKGROUND_REFLECTION_ON)
            glFrontFace(GL_CW);  //Front camera
        else
            glFrontFace(GL_CCW);   //Back camera
        
        int markerFound = state.getNumTrackableResults(); // number of detected markers
        
        // Commente ce bloc if si tu ne veux pas recacher ton sketch quand tu ne flashes plus le marqueur
        if (markerFound == 0) {
            NSLog(@"No marker found.");
            [[NSOperationQueue mainQueue] addOperationWithBlock:^{
                //[appDelegate hidePJS];
            }];
        }
        
        // Did we find any trackables this frame?
        for(int i = 0; i < state.getNumTrackableResults(); ++i) {
            // Get the trackable
            const QCAR::TrackableResult* trackableResult = state.getTrackableResult(i);
            //QCAR::Matrix44F modelViewMatrix = QCAR::Tool::convertPose2GLMatrix(trackableResult->getPose());
            
            // Check the type of the trackable:
            assert(trackableResult->getType() == QCAR::TrackableResult::MARKER_RESULT);
            const QCAR::MarkerResult* markerResult = static_cast<
                                        const QCAR::MarkerResult*>(trackableResult);
            const QCAR::Marker& marker = markerResult->getTrackable();
            
            int targetIndex = marker.getMarkerId();
                   
            //NSLog(@"Target index is %i", targetIndex);
            
            
            if (markerFound == 1 && targetIndex == 0) {
                NSLog(@"Marker 0 found.");
                [[NSOperationQueue mainQueue] addOperationWithBlock:^{
                    [appDelegate showPJS];
                }];
            }
            
            
            /*
            // Choose the object and texture based on the marker ID
            int textureIndex = marker.getMarkerId();
            
            assert(textureIndex < [textures count]);
             
            Object3D *obj3D = [objects3D objectAtIndex:textureIndex];
             
            // Render with OpenGL 2
            QCAR::Matrix44F modelViewProjection;
            ShaderUtils::translatePoseMatrix(-kLetterTranslate, -kLetterTranslate, 0.f, &modelViewMatrix.data[0]);
            ShaderUtils::scalePoseMatrix(kLetterScale, kLetterScale, kLetterScale, &modelViewMatrix.data[0]);
            ShaderUtils::multiplyMatrix(&qUtils.projectionMatrix.data[0], &modelViewMatrix.data[0], &modelViewProjection.data[0]);
            
            glUseProgram(shaderProgramID);
            
            glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, GL_FALSE, 0, obj3D.vertices);
            glVertexAttribPointer(normalHandle, 3, GL_FLOAT, GL_FALSE, 0, obj3D.normals);
            glVertexAttribPointer(textureCoordHandle, 2, GL_FLOAT, GL_FALSE, 0, obj3D.texCoords);
            
            glEnableVertexAttribArray(vertexHandle);
            glEnableVertexAttribArray(normalHandle);
            glEnableVertexAttribArray(textureCoordHandle);
            
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, [obj3D.texture textureID]);
            glUniformMatrix4fv(mvpMatrixHandle, 1, GL_FALSE, (GLfloat*)&modelViewProjection.data[0]);
            glUniform1i(texSampler2DHandle, 0); // GL_TEXTURE0
            glDrawElements(GL_TRIANGLES, obj3D.numIndices, GL_UNSIGNED_SHORT, obj3D.indices);
            
            ShaderUtils::checkGlError("FrameMarkers renderFrameQCAR");
             */
        }
        
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        glDisableVertexAttribArray(vertexHandle);
        glDisableVertexAttribArray(normalHandle);
        glDisableVertexAttribArray(textureCoordHandle);
        
        QCAR::Renderer::getInstance().end();
        [self presentFramebuffer];
    }
}

@end

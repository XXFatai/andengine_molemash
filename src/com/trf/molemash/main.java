package com.trf.molemash;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class main extends SimpleBaseGameActivity{
	
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	private TiledTextureRegion mMoleTextureRegine;
	
	private Scene scene;
	private Camera camera;
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this.getAssets(), "background.png", 0, 0);
		this.mMoleTextureRegine = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this.getAssets(), "mole.png", 320, 0, 1, 1);
		this.mBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene();
		scene.setBackgroundEnabled(false);
		Sprite backgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion, this.getVertexBufferObjectManager());
		Sprite moleSprite = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, mMoleTextureRegine, this.getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		scene.attachChild(moleSprite);
		return scene;
	}

}

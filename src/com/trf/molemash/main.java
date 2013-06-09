package com.trf.molemash;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;
import android.os.Debug;
import android.util.Log;

public class main extends SimpleBaseGameActivity {

	private String tag;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	private TiledTextureRegion mMoleTextureRegine;
	private Font mFont;

	private Text mScoreText;
	private int mScore = 0;
	private Sprite moleSprite;
	private float moleTop;
	private float moleLeft;

	private Scene scene;
	private ZoomCamera camera;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		this.camera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20);
		this.mFont.load();
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this.getAssets(),
						"chunsb.jpg", 0, 0);
		this.mMoleTextureRegine = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas,
						this.getAssets(), "mole.png", 480, 0, 1, 1);
		this.mBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub

		this.scene = new Scene();
		scene.setBackgroundEnabled(false);
		//添加多点触摸支持
		if(MultiTouch.isSupported(this)){
			this.mEngine.setTouchController(new MultiTouchController());
			this.mEngine.getEngineOptions().getTouchOptions().setNeedsMultiTouch(true);
		}
		mScoreText = new Text(0, 0, this.mFont, "score :  0", 
				this.getVertexBufferObjectManager());
		Sprite backgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion,
				this.getVertexBufferObjectManager());
		moleSprite = new Sprite(moleLeft, moleTop, mMoleTextureRegine,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					Log.i(tag, "-------touch-------");
					mScore = mScore + 10;
					mScoreText.setText("Score:" + mScore);
				}
				return true;
			}
		};
		
		scene.attachChild(backgroundSprite);
		scene.attachChild(moleSprite);
		scene.attachChild(mScoreText);		
		scene.registerTouchArea(moleSprite);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.registerUpdateHandler(new TimerHandler(1F, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler arg0) {
						// TODO Auto-generated method stub
						moveMole();
					}
				}));

		return scene;
	}

	// 改变mole的坐标，作用为移动mole
	private void moveMole() {
		moleLeft = (float) Math.random()
				* (CAMERA_WIDTH - moleSprite.getWidth());
		moleTop = (float) Math.random()
				* (CAMERA_HEIGHT - moleSprite.getHeight());
		moleSprite.setPosition(moleLeft, moleTop);
	}

}

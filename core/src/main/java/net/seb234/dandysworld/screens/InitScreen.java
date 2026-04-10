package net.seb234.dandysworld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


import net.seb234.dandysworld.BaseScreen;
import net.seb234.dandysworld.MainGame;

public class InitScreen  extends BaseScreen {

    private ProgressBar progressBar;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont myFont;

    @Override
    public void initialize() {
        // Initialization code here
        Stack stack = new Stack();
        stack.setFillParent(true);

        Texture backgroundTexture = new Texture("assets/background1.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setScaling(Scaling.fill);

        Texture logoTexture = new Texture("assets/dandyslogo.png");
        Image logoImage = new Image(logoTexture);

        ProgressBarStyle progressBarStyle = new ProgressBarStyle();

        NinePatch backgroundPatch = new NinePatch(new Texture("assets/progressbar_bg.png"), 4, 4, 4, 4);
        Texture fillTexture = new Texture("assets/progressbar_fill.png");

        progressBarStyle.background = new NinePatchDrawable(backgroundPatch);
        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));
        progressBarStyle.knob = new TextureRegionDrawable(new TextureRegion(fillTexture));
        progressBarStyle.knob.setMinWidth(0);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);

        TextButtonStyle menuButtonStyle = new TextButtonStyle();
        BitmapFont font = new BitmapFont(); // Use default font for simplicity

        progressBar = new ProgressBar(0, 100, 0.01f, false, progressBarStyle) {
            @Override
            public boolean setValue(float value) {
                boolean changed = super.setValue(value);
                
                // Si el valor llega al máximo, disparamos el evento
                if (this.getValue() >= getMaxValue()) {
                    logoImage.ad
                }
                return changed;
            }
        };


        progressBar.setValue(0);
        Table table = new Table();
        table.setFillParent(true);

        table.add(logoImage).padBottom(30);
        table.row();
        table.add(progressBar).width(400).height(30);

        stack.add(backgroundImage);
        stack.add(table);

        this.mainStage.addActor(stack);
        System.out.println("Loading Assets...");
    }

    @Override
    public void update(float dt) {
        if (progressBar.getValue() < 100) {
            progressBar.setValue(progressBar.getValue() + 0.1f);
        } else {
            
        }
    }
    
    public void loadFont() {
    // 2. Cargar el archivo físico de la fuente
    generator = new FreeTypeFontGenerator(Gdx.files.internal("mi_fuente_personalizada.ttf"));
    
    // 3. Configurar los parámetros (Tamaño, color, bordes...)
    parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 32;           // Tamaño en píxeles
    parameter.color = Color.WHITE;
    parameter.borderWidth = 2;      // Opcional: añade un borde
    parameter.borderColor = Color.BLACK;

    // 4. Generar la fuente lista para usar
    myFont = generator.generateFont(parameter);
    
    // 5. IMPORTANTE: El generador ya no se necesita, libéralo
    generator.dispose(); 
}
}

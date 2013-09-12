package wealk.android.jewels.entity;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import javax.microedition.khronos.opengles.GL10;

import wealk.android.jewels.constants.IConstants;

/**
 * 精灵接口的实现——钻石精灵
 *
 * @author qingfeng
 * @since 2010-11-03
 */
public class JewelSprite implements ISprite, IConstants {

    public static final int STYLE_BOOM = 7;

    // ===========================================================
    // Fields
    // ===========================================================

    int mStyle;  //钻石形状
    int mState;  //钻石状态
    final Sprite mSprite;  //钻石精灵
    final BorderSprite mBorderSprite;

    // ===========================================================
    // Constructors
    // ===========================================================

    public JewelSprite(int row, int col, TextureRegion mJewelTextureRegion, TextureRegion mBorderTextureRegion) {
        this.mSprite = new JewelCell(row, col, mJewelTextureRegion) {
            @Override
            public void setPosition(float pX, float pY) {
                super.setPosition(pX, pY);
                JewelSprite.this.mBorderSprite.setMapPosition(JewelSprite.this.getRow(), JewelSprite.this.getCol());
            }
        };
        this.mSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        this.mState = STATE_NORMAL;//默认是正常状态
        this.mBorderSprite = new BorderSprite(row, col, mBorderTextureRegion);
        this.mBorderSprite.getSprite().setVisible(false);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public int getRow() {
        return (int) this.mSprite.getX() / CELL_WIDTH;
    }

    @Override
    public int getCol() {
        return (int) this.mSprite.getY() / CELL_HEIGHT;
    }

    @Override
    public void setMapPosition(int row, int col) {
        int rowPos = row * CELL_WIDTH;
        int colPos = col * CELL_HEIGHT;
        this.mSprite.setPosition(rowPos, colPos);
        this.mBorderSprite.setMapPosition(rowPos, colPos);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setStyle(final int style) {
        this.mStyle = style;
    }

    public int getStyle() {
        return this.mStyle;
    }

    public Sprite getJewel() {
        return this.mSprite;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getState() {
        return this.mState;
    }

    public BorderSprite getBorderSprite() {
        return mBorderSprite;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    int step = 0;  //钻石缩小步骤

    public void doScale() {
        if (this.mState == STATE_SCALEINT) {
            if (step < 5) {
                step++;
                this.mSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                this.mSprite.setColor(1, 1, 1);
                switch (step) {
                    case 0:
                        this.mSprite.setScale(0.7f);
                        this.mSprite.setAlpha(0.5f);
                        break;
                    case 1:
                        this.mSprite.setScale(0.7f);
                        this.mSprite.setAlpha(0.4f);
                        break;
                    case 2:
                        this.mSprite.setScale(0.7f);
                        this.mSprite.setAlpha(0.3f);
                        break;
                    case 3:
                        this.mSprite.setScale(0.7f);
                        this.mSprite.setAlpha(0.2f);
                        break;
                    case 4:
                        this.mSprite.setScale(0.7f);
                        this.mSprite.setAlpha(0);
                        break;
                    default:
                        break;
                }
            } else {
                step = 0;
                this.mState = STATE_DEAD;
            }
        }//end if
    }

    public boolean equalsStyle(JewelSprite jewelSprite) {
        if(null == jewelSprite) {
            return false;
        }
        return this.getStyle() == jewelSprite.getStyle();
    }

    /**
     * 是否道具
     *
     * @param jewel
     * @return
     */
    public boolean isProps() {
        if(isBoom()) {
            return true;
        }
        return false;
    }

    /**
     * 是否炸弹
     *
     * @param jewel
     * @return
     */
    public boolean isBoom() {
        if(null != this && this.getStyle() == STYLE_BOOM) {
            return true;
        }
        return false;
    }

}

package ESP32data.Server;
//SourceDatas:TDoubles;插值前的一维数组
//Dir:Integer;在哪个方向和末尾插入2个值（0：前面；1：末尾）
//times:Integer多项式的项数，一次多项式是2项，二次多项式是3项
//返回值：插值后的一维数组（数量是插值前*4）
public class Insertdata {
    public static float[] insertdata(float[] sourcedata, int dir, int times) {
        int i, j, k;
        int arrcount;
        int startindex;

        String tempstr;
        float tempdou = 0;
        float[] coes = new float[6];
        arrcount = sourcedata.length;
        float[] Result = new float[arrcount * 4];
        if (dir == 0)
            startindex = 2;
        else
            startindex = 1;

        for (i = 0; i <= arrcount - 1; i++) {
            Result[startindex + i * 4] = sourcedata[i];
        }
        float[][] origindatas = new float[2][times];
        for (i = 0; i <= arrcount - times; i++) {
            for (j = 0; j <= times - 1; j++) {
                origindatas[0][j] = 4 * j;
                origindatas[1][j] = sourcedata[i + j];
            }

            Getpolydata(origindatas, times, coes);
            for (j = 1; j <= 4 - 1; j++) {
                if (times >= 2) tempdou = coes[0] + j * coes[1];
                if (times >= 3) tempdou = tempdou + j * j * coes[2];
                if (times >= 4) tempdou = tempdou + j * j * j * coes[3];
                Result[startindex + i * 4 + j] = tempdou;
            }
        }
        origindatas = new float[2][2];
        origindatas[0][0] = 0;
        origindatas[1][0] = sourcedata[0];
        origindatas[0][1] = 4;
        origindatas[1][1] = sourcedata[1];
        Getpolydata(origindatas, 2, coes);
        if (dir == 0) {
            tempdou = coes[0] + (-1) * coes[1];
            Result[1] = tempdou;
            tempdou = coes[0] + (-2) * coes[1];
            Result[0] = tempdou;

        } else {
            tempdou = coes[0] + (-1) * coes[1];
            Result[0] = tempdou;
        }
        for (i = arrcount - times; i <= arrcount - 2; i++) {
            for (j = 0; j <= 2 - 1; j++) {
                origindatas[0][j] = j * 4;
                origindatas[1][j] = sourcedata[i + j];
            }
            Getpolydata(origindatas, 2, coes);
            for (j = 1; j <= 4 - 1; j++) {
                tempdou = coes[0] + j * coes[1];
                Result[startindex + i * 4 + j] = tempdou;
            }
        }
        if (dir == 0) {
            tempdou = coes[0] + 5 * coes[1];
            Result[arrcount * 4 - 1] = tempdou;
        } else {
            tempdou = coes[0] + 5 * coes[1];
            Result[arrcount * 4 - 2] = tempdou;
            tempdou = coes[0] + 6 * coes[1];
            Result[arrcount * 4 - 1] = tempdou;
        }
        return Result;
    }

    static public float[] Getpolydata(float[][] origindata, int times, float[] coes) {
        float x1, x2, x3, x4, y1, y2, y3, y4;
        if (times < 2 || times > 4)
            times = 2;
        if (times == 2) {
            x1 = origindata[0][0];
            x2 = origindata[0][1];
            y1 = origindata[1][0];
            y2 = origindata[1][1];
            coes[1] = (y2 - y1) / x2;
            coes[0] = y1;
        } else if (times == 3) {
            x1 = origindata[0][0];
            x2 = origindata[0][1];
            x3 = origindata[0][2];
            y1 = origindata[1][0];
            y2 = origindata[1][1];
            y3 = origindata[1][2];
            coes[2] = ((y3 - y1) * x2 - (y2 - y1) * x3) / (x2 * x3 * x3 - x2 * x2 * x3);
            coes[1] = (y2 - y1) / x2 - coes[2] * x2;
            coes[0] = y1;
        } else if (times == 4) {
            x1 = origindata[0][0];
            x2 = origindata[0][1];
            x3 = origindata[0][2];
            x4 = origindata[0][3];
            y1 = origindata[1][0];
            y2 = origindata[1][1];
            y3 = origindata[1][2];
            y4 = origindata[1][3];
            coes[3] = ((y4 - y1) * x2 - (y2 - y1) * x4) / x2 - ((y3 - y1) * x2 - (y2 - y1) * x3) / (x2 * x3 * x3 - x2 * x2 * x3) * (x2 * x4 * x4 - x2 * x2 * x4) / x2;
            coes[3] = coes[3] / ((x2 * x4 * x4 * x4 - x2 * x2 * x2 * x4) / x2 - (x2 * x3 * x3 * x3 - x2 * x2 * x2 * x3) / (x2 * x3 * x3 - x2 * x2 * x3) * (x2 * x4 * x4 - x2 * x2 * x4) / x2);
            coes[2] = ((y3 - y1) * x2 - (y2 - y1) * x3) / (x2 * x3 * x3 - x2 * x2 * x3) - coes[3] * ((x2 * x3 * x3 * x3 - x2 * x2 * x2 * x3) / (x2 * x3 * x3 - x2 * x2 * x3));
            coes[1] = ((y2 - y1) - coes[2] * x2 * x2 - coes[3] * x2 * x2 * x2) / x2;
            coes[0] = y1;
        }
        return coes;
    }
}
